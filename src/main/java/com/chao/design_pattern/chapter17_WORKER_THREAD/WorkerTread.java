package com.chao.design_pattern.chapter17_WORKER_THREAD;

import java.util.Random;

public class WorkerTread extends Thread {
	private String name;
	private final Channel channel;

	private static final Random random = new Random(System.currentTimeMillis());

	public WorkerTread(String name, Channel channel) {
		super(name);
		this.channel = channel;
	}

	@Override
	public void run() {
		while (true) {
			Request request = channel.take();
			System.out.println(getName() + " take the msg -> " + request.getMsg());
			try {
				Thread.sleep(random.nextInt(1_000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
