package com.chao.concurrency_basic.chapter9.ProduceConsumerVersion1;

public class ProduceConsumer1 {
	private int i = 1;
	private final Object LOCK = new Object();

	private void produce(){
		synchronized (LOCK){
			System.out.println("P->"+i);
		}
	}	private void consumer(){
		synchronized (LOCK){
			System.out.println("C->"+i);
		}
	}

	public static void main(String[] args) {
		ProduceConsumer1 p = new ProduceConsumer1();

		new Thread("P"){
			@Override
			public void run() {
				while (true){
					p.produce();
				}
			}
		}.start();

		new Thread("C"){
			@Override
			public void run() {
				while (true){
					p.consumer();
				}
			}
		}.start();
	}

}
