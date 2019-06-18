package com.chao.design_pattern.chapter6_READ_WRITE_LOCK;

public class ReadWorker extends Thread {
	private SharedData sharedData;

	public ReadWorker(SharedData sharedData, String name) {
		super(name);
		this.sharedData = sharedData;
	}

	@Override
	public void run() {
		try {
			while (true) {
				String data = sharedData.read();
				System.out.println("The read thread [" + Thread.currentThread().getName() + "] print data : " + data);
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
