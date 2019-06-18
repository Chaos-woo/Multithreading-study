package com.chao.concurrency_basic.chapter1;

public class TryConcurrency {

	public static void main(String[] args) {
		new Thread("read-thread"){
			@Override
			public void run() {
				readFromDataBase();
			}
		}.start();

		new Thread("write-thread"){
			@Override
			public void run() {
				writeDataToFile();
			}
		}.start();
	}

	private static void readFromDataBase(){
		// read data from database and handle it
		try {
			print("Begin read data from db.");
			Thread.sleep(1000 * 10L);
			print("Read data done and start handle it.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		print("The data handle finish and successfully. -read");
	}

	private static void writeDataToFile(){
		// write data to file
		try {
			print("Begin write data to file.");
			Thread.sleep(1000 * 10L);
			print("Write data done and start handle it.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		print("The data handle finish and successfully. -write");
	}

	private static void print(String msg){
		System.out.println(msg);
	}

}
