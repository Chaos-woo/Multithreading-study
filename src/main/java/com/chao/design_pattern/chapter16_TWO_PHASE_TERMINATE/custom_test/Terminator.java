package com.chao.design_pattern.chapter16_TWO_PHASE_TERMINATE.custom_test;

public class Terminator extends Thread {
	private int number;
	private volatile boolean interrupt;

	public Terminator(){
		this.interrupt = false;
	}

	@Override
	public void run() {
		try {
			while (!interrupt){
				System.out.println("The number is " + number++);
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			System.out.println("The worker thread is interrupted...");
		}finally {
			doSave();
		}
	}

	private void doSave() {
		System.out.println("Terminator is saving...");
		try {
			Thread.sleep(1_000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Terminator has saved...");
	}

	public boolean isInterrupt() {
		return interrupt;
	}

	public void terminate(){
		this.interrupt = true;
		this.interrupt();
	}
}
