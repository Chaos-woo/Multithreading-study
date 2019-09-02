package com.chao._self.semaphore;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author W.Chao
 * @date 2019/9/2 18:52
 **/
public class SemaphoreTest {
	private static final int THREAD_COUNT = 30;
	private static Executor serviceThreadPool = Executors.newFixedThreadPool(THREAD_COUNT);

	private static Semaphore semaphore = new Semaphore(10);

	public static void main(String[] args) {
		for (int i = 0; i < THREAD_COUNT; i++) {
			serviceThreadPool.execute(()->{
				try {
					semaphore.acquire();
					System.out.println(Thread.currentThread().getName() + " save data");
					TimeUnit.SECONDS.sleep(2);
					semaphore.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
	}
}
