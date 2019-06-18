package com.chao.concurrency_basic.chapter13_SIMPLE_THREAD_POOL;

import java.util.Optional;
import java.util.stream.IntStream;

public class Test {

	public static void main(String[] args) {
		SimpleThreadPool simpleThreadPool = new SimpleThreadPool();
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
	}
}
