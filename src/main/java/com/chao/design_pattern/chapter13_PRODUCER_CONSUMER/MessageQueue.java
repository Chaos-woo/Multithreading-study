package com.chao.design_pattern.chapter13_PRODUCER_CONSUMER;

import java.util.LinkedList;

public class MessageQueue {
	private final LinkedList<Message> queue = new LinkedList<>();
	private final int limit;

	private static final int DEFAULT_MAX_LIMIT = 100;

	public MessageQueue() {
		this(DEFAULT_MAX_LIMIT);
	}

	public MessageQueue(int limit) {
		this.limit = limit;
	}

	public void put(Message message) {
		synchronized (queue){
			while (queue.size() > limit) {
				try {
					queue.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			queue.addLast(message);
			queue.notifyAll();
		}
	}

	public Message take() {
		synchronized (queue){
			while (queue.isEmpty()) {
				try {
					queue.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Message message = queue.removeFirst();
			queue.notifyAll();
			return message;
		}
	}

}
