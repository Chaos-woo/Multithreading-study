package com.chao.concurrency_basic.chapter6;

public class ThreadService {
	private Thread executeThread;
	private boolean finished = false;

	/**
	 * 线程执行
	 * @param task 线程任务
	 */
	public void execute(Runnable task){
		executeThread = new Thread(() -> {
			/**
			 * 将需要被执行的任务作为该工作线程的守护线程
			 * 目的是为了当工作线程结束，守护线程的生命周期就结束了
			 */
			Thread runner = new Thread(task);
			runner.setDaemon(true);

			runner.start();
			try {
				// 工作线程等待真正的任务线程工作完毕
				runner.join();
				// 执行到finished = true说明task完成了
				finished = true;
			} catch (InterruptedException e) {
//					e.printStackTrace();
			}
		});
		executeThread.start();
	}

	/**
	 * 强制结束
	 *
	 * @param mills 最长等待时间
	 */
	public void shutdown(long mills){
		// 方法被调用时间
		long currentTime = System.currentTimeMillis();
		/**
		 * 如果这里while()内的条件为true，表示任务完成，
		 * 否则任务没有完成，需要被强制结束
		 *
		 * 如果在调用此方法时，任务线程已经结束或在最长等待时间内结束了，
		 * (例如示例中，10秒最长等待时间，而任务线程5秒就结束了)
		 * 那么，finished会被置为true，那么会跳出循环，工作线程的run()方法也执行完成，那么作为守护线程的任务也被关闭
		 */
		while (!finished){
			// 任务未完成，每次循环都检测是否超过最长等待时间
			if((System.currentTimeMillis() - currentTime) >= mills){
				System.out.println("任务超时，需要结束");
				executeThread.interrupt();
				break;
			}

			// 用于捕获工作线程是否被中断
			try {
				executeThread.sleep(1);
			} catch (InterruptedException e) {
				System.out.println("执行线程被中断");
				break;
			}
		}
		// 重置
		// 若是任务在调用结束线程前完成（finished为true），那么重置即可
		finished = false;
	}

}
