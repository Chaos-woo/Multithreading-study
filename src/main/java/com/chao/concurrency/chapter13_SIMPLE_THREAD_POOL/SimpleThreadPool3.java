package com.chao.concurrency.chapter13_SIMPLE_THREAD_POOL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 增加自动扩充线程数量
 * 增加闲时自动回收
 */
public class SimpleThreadPool3 extends Thread {
	// 当前线程池线程数量
	private volatile int size;
	// 当前任务队列数量
	private int queueSize;
	// 当前拒绝策略
	private RejectPolicy rejectPolicy;
	// 线程池是否存活标识
	private volatile boolean destroy = false;
	// 三个线程个数大小关系：min < active < max
	// 最小保持的工作线程
	private int minThread;
	// 活动的工作线程
	private int activeThread;
	// 最大可用工作线程
	private int maxThread;

	// 默认最小保持的工作线程
	private static final int DEFAULT_MIN_THREAD = 4;
	// 默认活动的工作线程
	private static final int DEFAULT_ACTIVE_THREAD = 8;
	// 默认最大可用工作线程
	private static final int DEFAULT_MAX_THREAD = 16;
	// 任务队列默认大小
	private static final int DEFAULT_QUEUE_SIZE = 1000;
	// 任务队列
	private static final LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();
	private static final String PREFIX = "Simple-thread-";
	// 线程队列
	private static final ArrayList<WorkThread> THREAD_QUEUE = new ArrayList<>();
	// 将线程池中的所有线程归于一个线程组
	private static final ThreadGroup THREAD_GROUP = new ThreadGroup("Simple-ThreadGroup");
	// 线程名序列
	private static volatile int seq = 0;
	// 默认拒绝策略
	private static final RejectPolicy DEFAULT_REJECT_POLICY = () -> {
		throw new RejectPolicyException("Task Queue is full.");
	};

	public SimpleThreadPool3() {
		this(DEFAULT_MIN_THREAD, DEFAULT_ACTIVE_THREAD, DEFAULT_MAX_THREAD, DEFAULT_QUEUE_SIZE, DEFAULT_REJECT_POLICY);
	}

	public SimpleThreadPool3(int minThread, int activeThread, int maxThread, int queueSize, RejectPolicy rejectPolicy) {
		this.size = minThread;
		this.minThread = minThread;
		this.activeThread = activeThread;
		this.maxThread = maxThread;
		this.queueSize = queueSize;
		this.rejectPolicy = rejectPolicy;
		init();
	}

	private void init() {
		for (int i = 0; i < size; i++) {
			createWorkThread();
		}
		this.start();
	}

	// 创建一个线程
	private void createWorkThread() {
		WorkThread t = new WorkThread(THREAD_GROUP, PREFIX + (++seq));
		t.start();
		THREAD_QUEUE.add(t);
	}

	// 维护线程池中的线程数量
	@Override
	public void run() {
		while (!destroy) {
			/**
			 * 1.当最小线程数min不足以应付任务时，扩充线程至active个
			 * 2.当活动线程数active不足以应付任务时，扩充线程至max个
			 * 3.当任务队列为空，将那些空闲的线程杀死，减小至min个
			 * 任务队列为空或执行任务的线程少于最小线程数量，总是维护最小线程数量的线程
			 */
			if ((size < TASK_QUEUE.size() / 4) && (size < activeThread)) {
				synchronized (THREAD_QUEUE) {
					for (int i = size; i < activeThread; i++) {
						createWorkThread();
						size++;
					}
				}
			} else if ((size < TASK_QUEUE.size() / 2) && (size < maxThread)) {
				synchronized (THREAD_QUEUE) {
					for (int i = size; i < maxThread; i++) {
						createWorkThread();
						size++;
					}
				}
			}else if(TASK_QUEUE.isEmpty() && (size > minThread)){
				synchronized (THREAD_QUEUE){
					int threadCount = THREAD_QUEUE.size();
					closeThread(threadCount,minThread);
				}
			}

			try {
				Thread.sleep(1_000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.printf("min:%d, active:%d, max:%d, current:%d, size:%d,task_queue:%d", minThread, activeThread,
					maxThread, THREAD_GROUP.activeCount(), size,TASK_QUEUE.size());
			System.out.println();

		}
	}

	// 提交任务
	public void submit(Runnable runnable) {
		// 线程池被销毁不可再新增任务
		if (destroy)
			throw new RuntimeException("The Simple-Thread has destroyed and not allow to submit task.");
		// 超过当前任务队列的最大容量，执行拒绝策略
		if (TASK_QUEUE.size() > queueSize)
			rejectPolicy.reject();

		synchronized (TASK_QUEUE) {
			TASK_QUEUE.addLast(runnable);
			// 任务加入后，通知处于等待中的线程
			TASK_QUEUE.notifyAll();
		}
	}

	// 关闭线程池
	public void shutdown() throws InterruptedException {
		if (destroy)
			throw new RuntimeException("The Simple-Thread already destroyed.");
		// 等待任务队列中的所有任务完成
		while (!TASK_QUEUE.isEmpty()) {
			Thread.sleep(50);
		}
		// 逐个关闭线程
		synchronized (THREAD_QUEUE){
			int threadCount = THREAD_QUEUE.size();
			// 不断轮询所有线程，关闭线程
			closeThread(threadCount,0);
			destroy = true;
			System.out.println("The Simple-Thread is shutdown");
		}
	}

	// 关闭指定剩余数量的其他线程
	private void closeThread(int threadCount,int remainingThread){
		while (threadCount > remainingThread) {
			for (Iterator<WorkThread> it = THREAD_QUEUE.iterator();it.hasNext();) {
				WorkThread t = it.next();
				if ((threadCount > remainingThread) && (t.getTaskState() == TaskState.BLOCKED)) {
					t.close();
					t.interrupt();
					threadCount--;
					it.remove();
					size--;
				} else {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public int getSize() {
		return size;
	}

	public int getQueueSize() {
		return queueSize;
	}

	public RejectPolicy getRejectPolicy() {
		return rejectPolicy;
	}

	public boolean isDestroy() {
		return destroy;
	}

	public int getMinThread() {
		return minThread;
	}

	public int getActiveThread() {
		return activeThread;
	}

	public int getMaxThread() {
		return maxThread;
	}

	private enum TaskState {
		FREE, RUNNING, BLOCKED, DEAD
	}

	private class WorkThread extends Thread {
		private TaskState taskState = TaskState.FREE;

		WorkThread(ThreadGroup g, String name) {
			super(g, name);
		}

		/**
		 * 线程状态为非DEAD状态时，检测任务队列中是否有任务，有则开始接任务
		 * 当其他线程正在取任务时，其他线程等待（状态为阻塞状态）
		 * 从任务队列中取走任务（状态变为运行中），然后开始做任务
		 * 做完任务后（状态变为自由状态，等待下一个任务）
		 * 线程不结束，回到线程池中
		 */
		public void run() {
			OUTER:
			while (this.taskState != TaskState.DEAD) {
				Runnable runnable = null;
				synchronized (TASK_QUEUE) {
					while (TASK_QUEUE.isEmpty()) {
						try {
							this.taskState = TaskState.BLOCKED;
							TASK_QUEUE.wait();
						} catch (InterruptedException e) {
							// 当线程任务时被打断，回到线程池重新接任务
							break OUTER;
						}
						// 被唤醒之后检测到任务队列非空则退出循环接任务
					}
					// 拿到任务
					runnable = TASK_QUEUE.removeFirst();
				}
				// 执行任务，放在synchronized外，
				// 让线程执行任务的同时释放锁，让其他线程可以接任务
				if (runnable != null) {
					this.taskState = TaskState.RUNNING;
					runnable.run();
					this.taskState = TaskState.FREE;
				}
			}
		}

		private void close() {
			this.taskState = TaskState.DEAD;
		}

		private TaskState getTaskState() {
			return taskState;
		}
	}

	private static class RejectPolicyException extends RuntimeException {
		RejectPolicyException(String msg) {
			super(msg);
		}
	}

	private interface RejectPolicy {
		void reject() throws RejectPolicyException;
	}


}
