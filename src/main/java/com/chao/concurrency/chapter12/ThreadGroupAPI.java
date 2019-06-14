package com.chao.concurrency.chapter12;

public class ThreadGroupAPI {
	public static void main(String[] args) throws InterruptedException {
		ThreadGroup tg1 = new ThreadGroup("TG1");

		Thread t = new Thread(tg1,"t1"){
			@Override
			public void run() {
				try {
					Thread.sleep(5_000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

//		tg1.setDaemon(true);
		Thread.sleep(2_000);
		System.out.println(tg1.isDestroyed());
		tg1.destroy();
		System.out.println(tg1.isDestroyed());
	}


}
