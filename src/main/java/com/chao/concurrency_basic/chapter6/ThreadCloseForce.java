package com.chao.concurrency_basic.chapter6;

/**
 * 线程强制结束示例
 *
 * 由于线程被阻塞住，导致以下2种情况中的一种
 * 信号量检测 或 Thread.interrupted()方法无法使用
 * 这时候需要强制将线程结束
 */
public class ThreadCloseForce {

	public static void main(String[] args) {
		ThreadService service = new ThreadService();
		long start = System.currentTimeMillis();
		service.execute(()->{
			// 该情况表示线程被阻塞，或是执行时间过长
/*			while (true){

			}*/

			// 该情况表示线程被结束时在指定时间之内就可以结束，而不必等待指定时间的大小
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		});
		service.shutdown(10000);
		long end = System.currentTimeMillis();
		// 计算线程执行时间
		System.out.println(end - start);


	}
}

