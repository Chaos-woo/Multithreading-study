package com.chao.concurrency_basic.chapter4;

public class DaemonThread2 {

	public static void main(String[] args) {
		Thread t = new Thread(()->{
			Thread innerThread = new Thread(()->{
				while (true){
					// only run a once
					System.out.println("Do something...");
				}
			});

			innerThread.setDaemon(true);
			innerThread.start();
		});

		try {
			Thread.sleep(1_000);
			System.out.println("T thread finish done.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t.start();


	}
}
