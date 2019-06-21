package com.chao.design_pattern.chapter10;

import java.util.Random;

public class ThreadLocalTest {
	private static ThreadLocal threadLocal = new ThreadLocal();

	private static Random random = new Random(System.currentTimeMillis());

	// JVM start main thread
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(() -> {
			threadLocal.set("Thread-T1");
			try {
				Thread.sleep(random.nextInt(1000));
				System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		Thread t2 = new Thread(() -> {
			threadLocal.set("Thread-T2");
			try {
				Thread.sleep(random.nextInt(1000));
				System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println("========================");
		System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
	}
}
