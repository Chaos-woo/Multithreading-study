package com.chao.design_pattern.chapter13_PRODUCER_CONSUMER;

import java.util.Random;

public class ProducerThread extends Thread{
	private MessageQueue queue;
	private String name;
	private String msg;

	private static final Random random = new Random();

	public ProducerThread(MessageQueue queue, String name,String msg) {
		super(name);
		this.queue = queue;
		this.msg = msg;
	}

	@Override
	public void run() {
		while (true){
			Message message = new Message(msg);
			queue.put(message);
			System.out.println("ProducerThread-" + Thread.currentThread().getName() + " put message is " + message.getMsg());
			try {
				Thread.sleep(random.nextInt(1000));
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}
