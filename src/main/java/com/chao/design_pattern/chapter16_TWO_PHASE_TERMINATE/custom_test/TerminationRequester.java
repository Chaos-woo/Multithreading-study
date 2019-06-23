package com.chao.design_pattern.chapter16_TWO_PHASE_TERMINATE.custom_test;

public class TerminationRequester {
	public static void main(String[] args) throws InterruptedException {
		Terminator terminator = new Terminator();
		terminator.start();
		// 表示主线程做自己的工作
		Thread.sleep(5_000);
		// 发出指令终止线程
		terminator.terminate();
		// 主线程等待线程的终止
		terminator.join();

		System.out.println("Termination request is successful");
	}
}
