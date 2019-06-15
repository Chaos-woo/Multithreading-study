package com.chao.concurrency.chapter13_SIMPLE_THREAD_POOL;

import java.util.Optional;
import java.util.stream.IntStream;

public class Test2 {
	public static void main(String[] args) throws InterruptedException {
		SimpleThreadPool2 simpleThreadPool = new SimpleThreadPool2();
		IntStream.range(0,20)
				.forEach(i->simpleThreadPool.submit(()->{
					Optional.of("The task " + i + " is served by " + Thread.currentThread().getName() + " and start.")
							.ifPresent(System.out::println);
					try {
						Thread.sleep(1_000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Optional.of("The task " + i + " is served by " + Thread.currentThread().getName() + " and finished.")
							.ifPresent(System.out::println);
				}));

		simpleThreadPool.shutdown();
		Thread.sleep(20);
		// output: Exception in thread "main" java.lang.RuntimeException: The Simple-Thread already destroyed.
		simpleThreadPool.shutdown();
		// output: Exception in thread "main" java.lang.RuntimeException: The Simple-Thread has destroyed and not allow to submit task.
		simpleThreadPool.submit(()-> System.out.println("=========="));
	}
}
