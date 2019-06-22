package com.chao.design_pattern.chapter13_PRODUCER_CONSUMER;

import java.util.Random;

public class ConsumerThread extends Thread{
	private MessageQueue queue;

	private static final Random random = new Random();

	public ConsumerThread(MessageQueue queue, String name) {
		super(name);
		this.queue = queue;
	}

	@Override
	public void run() {
		while (true){
			Message message = queue.take();
			System.out.println("ConsumerThread-" + Thread.currentThread().getName() + " take a  message is " + message.getMsg());
			try {
				Thread.sleep(random.nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
