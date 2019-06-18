package com.chao.concurrency_basic.chapter4;

public class DaemonThread {
	public static void main(String[] args) {
		Thread t = new Thread(){
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("done.");
			}
		};

		// 设置为守护线程
		t.setDaemon(true);

		t.start();
	}
}
