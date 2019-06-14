package com.chao.concurrency.chapter9.differenceOfWaitAndSleep;

public class DifferenceOfWaitAndSleep {
	private static Object LOCK = new Object();

	public static void main(String[] args) {
		/*1. sleep()是Thread的方法，wait()是Object的方法
		  2. sleep()不会释放对象锁monitor(LOCK)，但是wait()会释放对象锁并且将这个对象放入等待队列
		  3. sleep()不需要依靠对象锁，但wait()需要
		  4. sleep()不需要被唤醒，而wait()需要*/
		m1();
		m2();
	}

	public static void m1(){
		try {
			Thread.sleep(2_000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void m2(){
		synchronized (LOCK){
			try {
				LOCK.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		/**
		 *
		 * output:Exception in thread "main" java.lang.IllegalMonitorStateException
		 */
		/*try {
			LOCK.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}


}
