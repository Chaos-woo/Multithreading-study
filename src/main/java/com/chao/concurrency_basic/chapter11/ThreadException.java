package com.chao.concurrency_basic.chapter11;

public class ThreadException {
	public static final int A = 10;
	public static final int B = 0;

	public static void main(String[] args) {
		Thread t = new Thread(()->{
			try {
				Thread.sleep(5_000);
				int result = A / B;
				System.out.println(result);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		/**
		 * 可以通过接口方法，捕获到异常之后，
		 * 对异常和发生异常的线程做一些事情
		 */
		t.setUncaughtExceptionHandler((thread,e)->{
			System.out.println(e);
			System.out.println(thread);
		});

		t.start();
	}
}
