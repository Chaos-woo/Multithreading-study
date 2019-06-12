package com.chao.concurrency.chapter5;

import java.util.Optional;
import java.util.stream.IntStream;

public class ThreadJoin2 {
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(()->{
			try {
				System.out.println("Thread t is running");
				Thread.sleep(10_000);
				System.out.println("Thread t is done");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		t1.start();
		t1.join(100,10);

		Optional.of("All of tasks finish done.").ifPresent(System.out::println);

		// main线程等待线程t1最多100毫秒10纳秒，如果这个时间内t1的工作没有完成，那么main线程开始工作
		IntStream.range(1,1000).forEach(i-> System.out.println(Thread.currentThread().getName() + "->" + i));

		// main线程虽然已经结束（在t1休眠的10秒内），但是由于t1属于活动的线程，所以要等待t1结束程序才会结束
	}

}
