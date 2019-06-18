package com.chao.concurrency_basic.chapter6;

public class ThreadCloseGraceful2 {

	public static void main(String[] args) {
		Worker w = new Worker();
		w.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		w.interrupt();
	}

	private static class Worker extends Thread{

		@Override
		public void run() {
			while (true){
				if(Thread.interrupted()){
					break;
				}
			}

			// do other thing...
		}

	}

}
