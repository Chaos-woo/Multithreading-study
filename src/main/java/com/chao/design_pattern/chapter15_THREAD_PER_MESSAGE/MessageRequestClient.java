package com.chao.design_pattern.chapter15_THREAD_PER_MESSAGE;

import java.util.stream.IntStream;

public class MessageRequestClient {

	public static void main(String[] args) {
		MessageHandle handle = new MessageHandle();
		IntStream.rangeClosed(1, 10).forEach(i -> new Thread(() -> {
			Message message = new Message(String.valueOf(i));
			handle.request(message);
		}).start());

		handle.shutdown();
	}
}
