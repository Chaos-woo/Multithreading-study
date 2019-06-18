package com.chao.concurrency_basic.chapter5;

public class ThreadJoin3 {

	public static void main(String[] args) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		Thread t1 = new Thread(new CaptureRunnable("M1",10_000L));
		Thread t2 = new Thread(new CaptureRunnable("M2",30_000L));
		Thread t3 = new Thread(new CaptureRunnable("M3",15_000L));

		t1.start();
		t2.start();
		t3.start();

		t1.join();
		t2.join();
		t3.join();

		long endTime = System.currentTimeMillis();
		System.out.printf("Save data begin timestamp is: %s, end timestamp：%s",startTime,endTime);
		System.out.println();
	}
}


class CaptureRunnable implements Runnable{
	private String machineName;
	private long spendTime;

	public CaptureRunnable(String machineName, long spendTime) {
		this.machineName = machineName;
		this.spendTime = spendTime;
	}

	public void getResult(){
		System.out.println(machineName + "finished.");
	}

	@Override
	public void run() {
		// do something
		try {
			Thread.sleep(spendTime);
			System.out.printf(machineName + " completed data capture timestamp is [%s] and successful.",System.currentTimeMillis());
			System.out.println();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
