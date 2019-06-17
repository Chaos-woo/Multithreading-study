package com.chao.concurrency.chapter12;

import java.util.Arrays;
import java.util.Collections;

public class ThreadGroupCreate {

	public static void main(String[] args) throws InterruptedException {

		ThreadGroup tg1 = new ThreadGroup("TG1");

		Thread t = new Thread(tg1,"t1"){
			@Override
			public void run() {
//				try {
//					Thread.sleep(100_000);
//					System.out.println(getThreadGroup().getName());
//					System.out.println(getThreadGroup().getParent().getName());
//					System.out.println(getThreadGroup().getParent().activeCount());
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				while (true){

				}
			}
		};

		t.start();

//		ThreadGroup tg2 = new ThreadGroup(tg1,"TG2");
//		System.out.println(tg2.getName());
//		System.out.println(tg2.getParent().getName());
//
//
//		System.out.println(Thread.currentThread().getName());
//		System.out.println(Thread.currentThread().getThreadGroup().getName());

		ThreadGroup tg2 = new ThreadGroup("TG2");
		Thread t2 = new Thread(tg2,"t2"){
			@Override
			public void run() {
				System.out.println(tg1.getName());
				Thread[] threads = new Thread[tg1.activeCount()];
				tg1.enumerate(threads);
				Arrays.asList(threads).forEach(System.out::println);
			}
		};

		t2.start();
	}
}
