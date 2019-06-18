package com.chao.concurrency_basic.chapter7;

public class TicketWindowRunnable implements Runnable {
	private int index = 1;
	private final static int MAX = 500;

	private final Object MONITOR = new Object();

	public void run() {
		while (true){
			synchronized (MONITOR){
				if(index > MAX)
					break;
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				System.out.println(Thread.currentThread() + "的号码是" + (index++));
			}
		}
	}
}
