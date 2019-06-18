package com.chao.concurrency_basic.chapter7;

public class SynchronizedThis {

	public static void main(String[] args) {
		ThisLock lock = new ThisLock();
		/**
		 * 这里线程T1先获得了锁，执行了m1方法，
		 * 之后线程T2才获得锁，执行m2方法
		 */
		new Thread(lock::m1,"T1").start();
		new Thread(lock::m2,"T2").start();
	}
}

class ThisLock{
	public synchronized void m1(){
		try {
			System.out.println(Thread.currentThread().getName());
			Thread.sleep(10_000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void m2(){
		try {
			System.out.println(Thread.currentThread().getName());
			Thread.sleep(10_000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
