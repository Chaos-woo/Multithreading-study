package com.chao.concurrency.chapter2.bank2;

public class TicketWindowRunnable implements Runnable {

	private int index = 1;

	private final static int MAX = 1000;

	public void run() {
		while (index <= MAX){
			System.out.println(Thread.currentThread() + "的号码是" + (index++));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
