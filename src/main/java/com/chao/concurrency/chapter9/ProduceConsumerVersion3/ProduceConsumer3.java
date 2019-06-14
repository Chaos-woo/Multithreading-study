package com.chao.concurrency.chapter9.ProduceConsumerVersion3;

import java.util.stream.Stream;

/**
 *
 *
 */
public class ProduceConsumer3 {
	private int i = 1;

	private final Object LOCK = new Object();
	private volatile boolean isProduced = false;

	private void produce(String p) {
		synchronized (LOCK) {
			/**
			 * 生产者检测产品是否被消费
			 * 没有被消费则一直处于被阻塞状态，直到被消费者唤醒
			 * 而消费者调用notifyAll()方法时，唤醒哪一个生产者由JVM的实现方法决定
			 */
			while (isProduced) {
				try {
					LOCK.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			i++;
			System.out.println(p+"->" + i);
			isProduced = true;
			// 将阻塞的所有线程唤醒
			LOCK.notifyAll();
		}
	}


	private void consumer(String c) {
		synchronized (LOCK) {
			/**
			 * 消费者检测到有可消费的产品就将其消费
			 * 随后通知生产者进行生产，之后自己进入被阻塞状态
			 * 直到被其他线程唤醒后再进行检测
			 */
			while (isProduced) {
				System.out.println(c+"->" + i);
				isProduced = false;
				// 将阻塞的所有线程唤醒
				LOCK.notifyAll();
			}
			try {
				LOCK.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	public static void main(String[] args) {
		ProduceConsumer3 p = new ProduceConsumer3();
		Stream.of("P1", "P2").forEach(n -> new Thread(n) {
			@Override
			public void run() {
				while (true) {
					p.produce(n);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start());

		Stream.of("C1", "C2", "C3").forEach(n -> new Thread(n) {
			@Override
			public void run() {
				while (true) {
					p.consumer(n);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start());
	}
}
