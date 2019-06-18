package com.chao.concurrency_basic.chapter3;

public class CreateThread3 {

	public static int counter = 0;

	public static void main(String[] args) {
		Thread t1 = new Thread(){
			@Override
			public void run() {
				try{
					add(0);
				}catch (Error e){
					// StackOverflowError
					e.printStackTrace();
					// output: 20662
					System.out.println(counter);
				}
			}

			private void add(int i) {
				++counter;
				add(i+1);
			}
		};

		t1.start();


		Thread t2 = new Thread(null, new Runnable() {
			@Override
			public void run() {
				try{
					add(0);
				}catch (Error e){
					// StackOverflowError
					e.printStackTrace();
					// output: 1026837
					System.out.println(counter);
				}
			}
			private void add(int i) {
				++counter;
				add(i+1);
			}
		}, "test", 1 << 24);

		t2.start();
	}
}
