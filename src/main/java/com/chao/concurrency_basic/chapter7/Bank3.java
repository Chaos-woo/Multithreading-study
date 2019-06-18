package com.chao.concurrency_basic.chapter7;

public class Bank3 {
	public static void main(String[] args) {
		final SynchronizedRunnable ticketWindow = new SynchronizedRunnable();
		Thread t1 = new Thread(ticketWindow,"1号窗口");
		Thread t2 = new Thread(ticketWindow,"2号窗口");
		Thread t3 = new Thread(ticketWindow,"3号窗口");

		t1.start();
		t2.start();
		t3.start();
	}
}
