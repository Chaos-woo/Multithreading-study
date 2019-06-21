package com.chao.design_pattern.chapter14_CUSTOM_COUNT_DOWN_LATCH;

public class CountDown {
	private int total;
	private int count;

	public CountDown(int total) {
		this.total = total;
		this.count = 0;
	}

	public void cut(){
		synchronized (this){
			total--;
			this.notifyAll();
		}
	}

	public void await() throws InterruptedException {
		synchronized (this){
			while (count != total){
				this.wait();
			}
		}
	}
}
