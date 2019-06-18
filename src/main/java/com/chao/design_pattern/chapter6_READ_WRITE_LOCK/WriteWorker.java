package com.chao.design_pattern.chapter6_READ_WRITE_LOCK;

public class WriteWorker extends Thread {
	private SharedData sharedData;
	private String string;
	private  int index = 0;

	public WriteWorker(SharedData sharedData, String name, String string) {
		super(name);
		this.sharedData = sharedData;
		this.string = string;
	}

	@Override
	public void run() {

		try {
			while (true) {
				char c = string.charAt(nextIndex());
				sharedData.write(c);
				System.out.println("The write thread [" + Thread.currentThread().getName() + "] write a character[" + c + "]");
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private int nextIndex() {
		index++;
		if (index > string.length() - 1) {
			index = 0;
		}
		return index;
	}


}
