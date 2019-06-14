package com.chao.concurrency.chapter10_SELF_LOCK;

import java.util.Optional;
import java.util.stream.Stream;

public class LockTest {
	private static final BooleanLock LOCK = new BooleanLock();

	public static void main(String[] args) {
		Stream.of("T1","T2","T3","T4")
				.forEach(name->{
					new Thread(()->{
						try {
							LOCK.lock();
							Optional.of(Thread.currentThread().getName() + " get the lock")
									.ifPresent(System.out::println);
							System.out.println(LOCK.getBlockedCollection());
							Thread.sleep(5_000);
						}
//						catch (Lock.TimeoutException e) {
//							Optional.of(Thread.currentThread().getName() + " Timeout")
//									.ifPresent(System.out::println);
//						}
						catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
							LOCK.unlock();
						}
					},name).start();
				});
	}
}
