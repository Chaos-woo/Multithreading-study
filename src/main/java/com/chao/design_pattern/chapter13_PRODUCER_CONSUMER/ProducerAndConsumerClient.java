package com.chao.design_pattern.chapter13_PRODUCER_CONSUMER;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ProducerAndConsumerClient {
	public static void main(String[] args) {
		MessageQueue queue = new MessageQueue();
		AtomicInteger goods = new AtomicInteger();

		IntStream.range(1,3)
				.forEach(i-> new ProducerThread(queue, String.valueOf(i), String.valueOf(goods.getAndIncrement())).start());

		IntStream.range(1,10)
				.forEach(i-> new ConsumerThread(queue, String.valueOf(i)).start());
	}
}
