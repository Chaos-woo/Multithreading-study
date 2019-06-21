package com.chao.design_pattern.chapter15_THREAD_PER_MESSAGE;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageHandle {
	private final ExecutorService executor = Executors.newFixedThreadPool(5);
	private static final Random random = new Random(System.currentTimeMillis());

	public void request(Message message){
		executor.execute(()->{
			String msg = message.getMsg();
			System.out.println(Thread.currentThread().getName() + " get msg is " + msg);
			try {
				Thread.sleep(random.nextInt(1_000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	public void shutdown(){

		// 调用了shutdown()方法后，不等待线程执行任务完成，立即关闭线程池
		// 原因未知
		executor.shutdown();
	}
}
