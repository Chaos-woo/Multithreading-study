package com.chao._self.thread_pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Description: 一个易于理解的线程池实现
 *
 * 鲁棒性不如concurrency_basic.chapter13_SIMPLE_THREAD_POOL包下的SimpleThreadPool3
 *
 * @author W.Chao
 * @date 2019/8/21 19:03
 **/
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {
	// 线程池最大数量
	private static final int MAX_WORKER_NUMBERS = 10;
	// 线程池默认数量
	private static final int DEFAULT_WORKER_NUMBERS = 5;
	// 线程池最小数量
	private static final int MIN_WORKER_NUMBERS = 1;
	// 任务等待列表
	private final LinkedList<Job> jobs = new LinkedList<>();
	// 工作者列表
	private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());
	// 当前工作者数量
	private int workerNum = DEFAULT_WORKER_NUMBERS;
	// 编号生成
	private AtomicLong threadNum = new AtomicLong();

	// 缺省情况下使用默认线程数量
	public DefaultThreadPool() {
		initializeWorkers(DEFAULT_WORKER_NUMBERS);
	}

	// 初始化工作者线程
	private void initializeWorkers(int num) {
		for (int i = 0; i < num; i++) {
			Worker worker = new Worker();
			workers.add(worker);
			Thread thread = new Thread(worker, "ThreadPool-Worker-" + threadNum.getAndIncrement());
			thread.start();
		}
	}

	// 有參时通过比较看最后得出需要初始化的工作者线程数量
	public DefaultThreadPool(int num) {
		workerNum = num > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS : num;
		initializeWorkers(workerNum);
	}

	/**
	 * 提交任务，只是将任务加入任务队列，之后唤醒队列中等待的线程
	 * @param job
	 */
	@Override
	public void execute(Job job) {
		if (job != null) {
			synchronized (jobs) {
				jobs.addLast(job);
				jobs.notifyAll();
			}
		}
	}

	@Override
	public void shutdown() {
		workers.forEach(Worker::shutdown);
	}

	/**
	 * 添加新的工作者线程时，计算出需要添加的数目，
	 * 再使用初始化工作者线程方法添加
	 *
	 * 添加之前需要进行工作者线程数量的验证
	 * @param num
	 */
	@Override
	public void addWorker(int num) {
		synchronized (jobs) {
			if (num + this.workerNum > MAX_WORKER_NUMBERS) {
				// 超出最大数量之后，将需要添加的数量减少至不超过最大数量的数值
				num = MAX_WORKER_NUMBERS - this.workerNum;
				initializeWorkers(num);
				this.workerNum += num;
			}
		}
	}

	@Override
	public void removeWorker(int num) {
		synchronized (jobs) {
			// 防止需要删除的工作者线程大于（超出范围）或等于（默认最小线程无法达成）workNum
			if (num >= this.workerNum) {
				throw new IllegalArgumentException("beyond workNum");
			}
			for (int i = 0; i < num; ) {
				Worker worker = workers.get(i);
				if (workers.remove(worker)) {
					worker.shutdown();
					i++;
				}
			}
			this.workerNum -= num;
		}
	}

	public int getWorkerNum() {
		return workerNum;
	}

	class Worker implements Runnable {
		private volatile boolean running = true;

		@Override
		public void run() {
			while (running) {
				Job job = null;
				synchronized (jobs) {
					// 如果jobs（工作列表）是空的，那么线程等待
					while (jobs.isEmpty()) {
						try {
							jobs.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							return;
						}
					}
					// 取出一个job
					job = jobs.removeFirst();
				} //这里重要的一点是：获取到任务之后应该释放锁，让其他线程有机会获取锁并获得任务
				if (job != null) {
					try {
						job.run();
					} catch (Exception e) {
						// ignore
					}
				}
			}
		}

		public void shutdown() {
			running = false;
		}
	}

}
