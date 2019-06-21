package com.chao.design_pattern.chapter8_FUTURE;

public class FutureTest {
	public static void main(String[] args) throws InterruptedException {
		FutureService futureService = new FutureService();
		Future future = futureService.submit(() -> {
			// 给FutureTask call()方法的任务
			try {
				Thread.sleep(10_000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "FINISHED";
		});

		System.out.println("==========");
		System.out.println("do other thing");
		System.out.println("==========");
		/**
		 * 假设主线程需要做的事情约为15秒，那么15秒后，
		 * 分配给futureService做的任务（10秒）已经完成，
		 * 主线程取走结果不花费多余的时间（比如等待10秒）
		 *
		 * 如果主线程需要做的事情只有1秒，那么1秒后，
		 * 分配给分配给futureService做的任务（10秒）还需要9秒才能完成，
		 * 那么主线程取走结果（调用AsynFuture的get()）需要等待9秒
		 *
		 * 总体上来说，这样两个线程做任务可以实现并行，减少时间等待
		 */
		Thread.sleep(1_000);
		long startTime = System.currentTimeMillis();
		System.out.println(future.get());
		long endTime = System.currentTimeMillis();
		System.out.println("取出结果花费的时间：" + (endTime - startTime));
		System.out.println("==========");
	}
}
