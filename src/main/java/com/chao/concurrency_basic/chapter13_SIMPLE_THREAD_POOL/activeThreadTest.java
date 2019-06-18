package com.chao.concurrency_basic.chapter13_SIMPLE_THREAD_POOL;

public class activeThreadTest {
	private static volatile int seq = 0;
	private static final Object LOCK = new Object();
	public static void main(String[] args) throws InterruptedException {
		ThreadGroup tg = new ThreadGroup("TG");
		for(int i = 0;i<5;i++){
			Thread t = new Thread(tg,new Integer(seq++).toString()){
				@Override
				public void run() {
					synchronized (LOCK){
						try {
							LOCK.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};
			t.start();
		}

		for(int i = 0;i<5;i++){
			Thread t = new Thread(tg,new Integer(seq++).toString()){
				@Override
				public void run() {
					while (true){}
				}
			};
			t.start();
		}

		Thread.sleep(100);
		System.out.println(tg.activeCount());
	}
}
