package com.chao.design_pattern.chapter17_WORKER_THREAD;

import java.util.Random;

public class TransportThread extends Thread {
	private String name;
	private final Channel channel;
	private final Random random = new Random(System.currentTimeMillis());

	public TransportThread(String name, Channel channel) {
		this.name = name;
		this.channel = channel;
	}

	@Override
	public void run() {
		while (true) {
			String msg = String.valueOf(random.nextInt(100));
			Request request = new Request(msg);
			channel.put(request);
			System.out.println(name + " put the msg -> " + msg);
			try {
				Thread.sleep(random.nextInt(1_000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
