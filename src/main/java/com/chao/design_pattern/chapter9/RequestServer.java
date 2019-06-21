package com.chao.design_pattern.chapter9;

import java.util.LinkedList;
import java.util.Random;

public class RequestServer extends Thread {
	private final RequestQueue queue;
	private final Random random;
	private volatile boolean closed = false;

	public RequestServer(RequestQueue queue) {
		this.queue = queue;
		this.random = new Random();
	}

	@Override
	public void run() {
		while (!closed) {
			Request request = queue.popRequest();
			// 被中断取到的请求为null
			if (null == request) {
				continue;
			}
			System.out.println("Server -> " + request.getValue());
			try {
				Thread.sleep(random.nextInt(1000));
			} catch (InterruptedException e) {
				System.out.println("The server is closed");
				break;
			}
		}
	}

	public void close() {
		this.closed = true;
		this.interrupt();
	}
}
