package com.chao.concurrency.chapter6;

public class ThreadInterrupt {
	private static final Object MONITOR = new Object();

	public static void main(String[] args){
		/*Thread t = new Thread(){
			@Override
			public void run() {
				while (true){
					synchronized (MONITOR){
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							System.out.println("Interrupt...");
							break;
						}
					}
				}
			}
		};

		t.start();
		Thread.sleep(1000);

		System.out.println(t.isInterrupted());
		t.interrupt();
		System.out.println(t.isInterrupted());*/

		/*Thread t = new Thread(()->{
			while (true){
				synchronized (MONITOR){
					try {
						MONITOR.wait(10);
					} catch (InterruptedException e) {
						System.out.println(Thread.interrupted());
						break;
					}
				}
			}
		});*/


		Thread t = new Thread(){
			@Override
			public void run() {
				while (true){

				}
			}
		};

		t.start();
		Thread main = Thread.currentThread();
		Thread t2 = new Thread(){
			@Override
			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				main.interrupt();
				System.out.println("interrupt");
			}
		};

		t2.start();

		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}
}
