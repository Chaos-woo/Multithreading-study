package com.chao.concurrency_basic.chapter7;

public class SynchronizedRunnable implements Runnable {
	private int index = 1;
	private final static int MAX = 500;

	public void run() {
		while (true){
				/*if(index > MAX)
					break;
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread() + "的号码是" + (index++));*/

				if(ticket()){
					break;
				}

		}
	}

	private boolean ticket(){
		synchronized (this){
			// 读取index
			if(index > MAX)
				return true;
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// index++ -> index = index + 1
			// 1. 读取index
			// 2. index = index + 1
			// 3. 将新index返回
			System.out.println(Thread.currentThread() + "的号码是" + (index++));
		}
		return false;
	}

}
