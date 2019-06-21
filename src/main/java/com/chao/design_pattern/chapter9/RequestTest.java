package com.chao.design_pattern.chapter9;

public class RequestTest {
	public static void main(String[] args) throws InterruptedException {
		RequestQueue queue = new RequestQueue();
		new RequestClient(queue, "leo").start();
		RequestServer server = new RequestServer(queue);
		server.start();

		Thread.sleep(10_000);
		server.close();
	}
}
