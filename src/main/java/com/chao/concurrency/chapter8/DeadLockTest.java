package com.chao.concurrency.chapter8;

public class DeadLockTest {
	public static void main(String[] args) {
		OtherService otherService = new OtherService();
		DeadLock deadLock = new DeadLock(otherService);
		otherService.setDeadLock(deadLock);

		/**
		 * cmd通过jps命令查看该main线程的端口号
		 * 再通过jstack + 上面查到的端口号的命令查看栈情况
		 * 可以分析出来死锁情况：是否存在死锁
		 */
		new Thread(()->{
			while (true){
				deadLock.d1();
			}
		}).start();
		new Thread(()->{
			while (true){
				otherService.o1();
			}
		}).start();

	}

}
