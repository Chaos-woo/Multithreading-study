package com.chao.concurrency.chapter13_SIMPLE_THREAD_POOL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 1.实现了简单的线程池的创建、任务队列、
 * 维护线程池中线程的状态、线程接受任务并执行
 *
 * 增加拒绝策略
 * 增加线程池关闭
 */
public class SimpleThreadPool2 {
	private int size;
	private int queueSize;
	private RejectPolicy rejectPolicy;
	private volatile boolean destroy;

	// 线程池默认大小为10
	private static final int DEFAULT_SIZE = 10;
	// 任务队列默认大小
	private static final int DEFAULT_QUEUE_SIZE = 1000;
	// 任务队列
	private static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();
	private static final String PREFIX = "Simple-thread-";
	// 线程队列
	private static final ArrayList<WorkThread> THREAD_QUEUE = new ArrayList<>();
	// 将线程池中的所有线程归于一个线程组
	private static final ThreadGroup THREAD_GROUP = new ThreadGroup("Simple-ThreadGroup");
	// 线程名序列
	private static volatile int seq = 0;
	// 默认拒绝策略
	private static final RejectPolicy DEFAULT_REJECT_POLICY= ()->{throw new RejectPolicyException("Task Queue is full.");};

	public SimpleThreadPool2() {
		this(DEFAULT_SIZE,DEFAULT_QUEUE_SIZE,DEFAULT_REJECT_POLICY);
	}

	public SimpleThreadPool2(int size,int queueSize,RejectPolicy rejectPolicy) {
		this.size = size;
		this.queueSize = queueSize;
		this.rejectPolicy = rejectPolicy;
		init();
	}

	private void init() {
		for (int i = 0; i < size; i++) {
			createWorkThread();
		}
	}

	// 创建一个线程
	private void createWorkThread() {
		WorkThread t = new WorkThread(THREAD_GROUP, PREFIX + (++seq));
		t.start();
		THREAD_QUEUE.add(t);
	}

	// 提交任务
	public void submit(Runnable runnable) {
		// 线程池被销毁不可再新增任务
		if(destroy)
			throw new RuntimeException("The Simple-Thread has destroyed and not allow to submit task.");
		// 超过当前任务队列的最大容量，执行拒绝策略
		if(TASK_QUEUE.size() > queueSize)
			rejectPolicy.reject();

		synchronized (TASK_QUEUE) {
			TASK_QUEUE.addLast(runnable);
			// 任务加入后，通知处于等待中的线程
			TASK_QUEUE.notifyAll();
		}
	}

	// 关闭线程池
	public void shutdown() throws InterruptedException {
		if(destroy)
			throw new RuntimeException("The Simple-Thread already destroyed.");
		// 等待任务队列中的所有任务完成
		while (!TASK_QUEUE.isEmpty()){
			Thread.sleep(50);
		}
		// 逐个关闭线程
		synchronized (THREAD_QUEUE){
			int threadCount = THREAD_QUEUE.size();
			// 不断轮询所有线程，关闭线程
			while(threadCount > 0){
				for (Iterator<WorkThread> it = THREAD_QUEUE.iterator();it.hasNext();){
					WorkThread t = it.next();
					if(t.getTaskState() == TaskState.BLOCKED){
						t.close();
						t.interrupt();
						threadCount--;
						it.remove();
					}else {
						Thread.sleep(100);
					}
				}
			}
			destroy = true;
			System.out.println("The Simple-Thread is shutdown");
			System.out.println(THREAD_QUEUE.size());
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
							this.taskState = TaskState.FREE;
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

	private static class RejectPolicyException extends RuntimeException{
		RejectPolicyException(String msg){
			super(msg);
		}
	}

	private interface RejectPolicy{
		void reject() throws RejectPolicyException;
	}


}
