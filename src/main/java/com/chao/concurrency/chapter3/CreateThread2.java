package com.chao.concurrency.chapter3;

import java.util.Arrays;

public class CreateThread2 {
	public static void main(String[] args) {
		Thread t = new Thread(()->{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		t.start();
//		System.out.println(t.getThreadGroup());
//		System.out.println(Thread.currentThread().getName());
//		System.out.println(Thread.currentThread().getThreadGroup());

		ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
		System.out.println(threadGroup.activeCount());

		Thread[] threads = new Thread[threadGroup.activeCount()];
		threadGroup.enumerate(threads);
		Arrays.asList(threads).forEach(System.out::println);

	}
}
