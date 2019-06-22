package com.chao.design_pattern.chapter17_WORKER_THREAD;

public class WorkerThreadTest {
	public static void main(String[] args) {
		Channel channel = new Channel(5);
		// 隐藏了工作线程的显示创建和运行
		channel.startWork();

		new TransportThread("leo", channel).start();
		new TransportThread("alex", channel).start();
		new TransportThread("papa", channel).start();
	}
}
