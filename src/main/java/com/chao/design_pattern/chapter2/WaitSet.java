package com.chao.design_pattern.chapter2;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * 1.所有的对象都会有一个wait set，用来存放调用了该对象wait()方法之后进入block状态线程
 * 2.线程被notify()之后不一定立即得到执行
 * 3.线程从wait set中被唤醒的顺序不一定是FIFO，根据JVM的实现来决定
 * 4.线程执行wait()方法之后，会释放所持有的锁，被唤醒之后再一次获取锁，之后从执行wait()方法后的代码继续执行
 */
public class WaitSet {

	private static final Object LOCK = new Object();

	private static void work(){
		synchronized (LOCK){
			System.out.println("Begin...");

			try {
				System.out.println("Thread will coming...");
				LOCK.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Thread will out...");
		}
	}

	public static void main(String[] args) {
		/*IntStream.rangeClosed(1,10).forEach(i-> new Thread(String.valueOf(i)){
			@Override
			public void run() {
				synchronized (LOCK){
					Optional.of(Thread.currentThread().getName()+" into wait set.").ifPresent(System.out::println);
					try {
						LOCK.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Optional.of(Thread.currentThread().getName()+" out wait set.").ifPresent(System.out::println);
				}
			}
		}.start());

		try {
			Thread.sleep(5_000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		IntStream.rangeClosed(1,3).forEach(i-> new Thread(String.valueOf(i)){
			@Override
			public void run() {
				synchronized (LOCK){
					LOCK.notify();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start());*/
		new Thread(WaitSet::work).start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized (LOCK){
			LOCK.notify();
		}
	}
}
