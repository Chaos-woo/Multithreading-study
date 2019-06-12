package com.chao.concurrency.chapter6;

public class ThreadInterrupt {
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(){
			@Override
			public void run() {
				super.run();
			}
		};

		t.start();
		Thread.sleep(1000);

		System.out.println(t.isInterrupted());
		t.interrupt();
		System.out.println(t.isInterrupted());
	}
}
