package com.chao.concurrency.chapter13_SIMPLE_THREAD_POOL;

import java.util.Optional;
import java.util.stream.IntStream;

public class Test3 {
	public static void main(String[] args) throws InterruptedException {
		SimpleThreadPool3 simpleThreadPool = new SimpleThreadPool3();
		IntStream.range(0,30)
				.forEach(i->simpleThreadPool.submit(()->{
					Optional.of("The task " + i + " is served by " + Thread.currentThread().getName() + " and start.")
							.ifPresent(System.out::println);
					try {
						Thread.sleep(3_000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Optional.of("The task " + i + " is served by " + Thread.currentThread().getName() + " and finished.")
							.ifPresent(System.out::println);
				}));

		Thread.sleep(5_000);
		System.out.println("==========================");


		IntStream.range(0,10)
				.forEach(i->simpleThreadPool.submit(()->{
					Optional.of("The task " + i + " is served by " + Thread.currentThread().getName() + " and start.")
							.ifPresent(System.out::println);
					try {
						Thread.sleep(3_000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Optional.of("The task " + i + " is served by " + Thread.currentThread().getName() + " and finished.")
							.ifPresent(System.out::println);
				}));

		// 测试：最后在1个线程工作时，是否能维持最小线程数在线程池中，
		// 这个测试不喝shutdown()一起调用
//		simpleThreadPool.submit(()->{
//			while (true){
//
//			}
//		});

		simpleThreadPool.shutdown();
	}
}
