package com.chao._self.thread_pool;

import java.util.Optional;
import java.util.stream.IntStream;

public class Test3 {
	public static void main(String[] args) throws InterruptedException {
		DefaultThreadPool<Runnable> defaultThreadPool = new DefaultThreadPool<>();
		IntStream.range(0, 30)
				.forEach(i -> defaultThreadPool.execute(() -> {
					Optional.of("The task " + i + " is served by " + Thread.currentThread().getName() + " and start.")
							.ifPresent(System.out::println);
					try {
						Thread.sleep(3_000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Optional.of("The task " + i + " is served by " + Thread.currentThread().getName() + " and finished.")
							.ifPresent(System.out::println);
					Optional.of("Current workers has " + defaultThreadPool.getWorkerNum() + " workers")
							.ifPresent(System.out::println);
				}));

		Thread.sleep(5_000);
		System.out.println("==========================");
		defaultThreadPool.addWorker(10);

		IntStream.range(0, 50)
				.forEach(i -> defaultThreadPool.execute(() -> {
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

	}
}
