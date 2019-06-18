package com.chao.concurrency_basic.chapter6;

public class ThreadCloseGraceful {

	public static void main(String[] args) {
		Worker w = new Worker();
		w.start();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		w.shutdown();
	}


	private static class Worker extends Thread{
		private volatile boolean start = true;

		@Override
		public void run() {
			while (start){
				// do something
			}
		}

		public void shutdown(){
			this.start = false;
		}
	}
}
