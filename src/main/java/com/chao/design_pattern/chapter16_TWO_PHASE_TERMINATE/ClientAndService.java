package com.chao.design_pattern.chapter16_TWO_PHASE_TERMINATE;

public class ClientAndService {
	public static void main(String[] args) throws InterruptedException {
		AppServer server = new AppServer();
		server.start();

		Thread.sleep(50_000);
		server.shutdown();
	}
}
