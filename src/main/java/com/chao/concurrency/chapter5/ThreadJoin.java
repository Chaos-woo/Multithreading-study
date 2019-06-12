package com.chao.concurrency.chapter5;

import java.util.Optional;
import java.util.stream.IntStream;

public class ThreadJoin {

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(()->{
			IntStream.range(1,1000).forEach(i-> System.out.println(Thread.currentThread().getName() + "->" + i));
		});
		Thread t2 = new Thread(()->{
			IntStream.range(1,1000).forEach(i-> System.out.println(Thread.currentThread().getName() + "->" + i));
		});
		t1.start();
		t2.start();
		t1.join();
		t2.join();

		Optional.of("All of tasks finish done.").ifPresent(System.out::println);

		// main线程等待上面两个线程结束后才开始执行注释下方的工作；而线程t1和t2是并行执行的
		IntStream.range(1,1000).forEach(i-> System.out.println(Thread.currentThread().getName() + "->" + i));

	}
}
