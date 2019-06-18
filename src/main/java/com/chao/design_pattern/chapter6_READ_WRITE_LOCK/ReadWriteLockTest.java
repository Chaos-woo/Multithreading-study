package com.chao.design_pattern.chapter6_READ_WRITE_LOCK;

public class ReadWriteLockTest {
	public static void main(String[] args) {
		SharedData data = new SharedData(10);
		new ReadWorker(data, "1").start();
		new ReadWorker(data, "2").start();
		new ReadWorker(data, "3").start();
		new ReadWorker(data, "4").start();
		new ReadWorker(data, "5").start();

		new WriteWorker(data, "1", "qwertyuiop").start();
		new WriteWorker(data, "2", "QWERTYUIOP").start();
	}

}
