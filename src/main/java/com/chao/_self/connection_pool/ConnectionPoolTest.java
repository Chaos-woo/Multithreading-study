package com.chao._self.connection_pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description:
 *
 * @author W.Chao
 * @date 2019/8/21 16:28
 **/
public class ConnectionPoolTest {
	private static ConnectionPool pool = new ConnectionPool(10);
	// 保证所有ConnectionRunner能够同时开始
	private static CountDownLatch start = new CountDownLatch(1);
	// main线程将会等待所有ConnectionRunner结束后才能继续执行
	private static CountDownLatch end;

	public static void main(String[] args) {
		// 线程数量，可以修改线程数量进行观察
		int threadCount = 50;
		end = new CountDownLatch(threadCount);
		int count = 20;
		AtomicInteger got = new AtomicInteger();
		AtomicInteger notGot = new AtomicInteger();
		for (int i = 0; i < threadCount; i++) {
			Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread");
			thread.start();
		}
		try {
			start.countDown();
			// main主线程等待所有的线程完成工作
			end.await();
			System.out.println("total invoke: " + (threadCount * count));
			System.out.println("got connection: " + got);
			System.out.println("not got connection " + notGot);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static class ConnectionRunner implements Runnable {
		// 尝试获取次数
		int count;
		// 获得的次数
		AtomicInteger got;
		// 未获得的次数
		AtomicInteger notGot;

		public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
			this.count = count;
			this.got = got;
			this.notGot = notGot;
		}

		@Override
		public void run() {
			try {
				start.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
			while (count > 0) {
				try {
					// 从线程池中获取连接，如果1000ms内无法获取到，将会返回null
					// 分别统计连接获取的数量got和未获取到的数量notGot
					Connection connection = pool.fetchConnection(1000);
					if (connection != null) {
						try {
							connection.createStatement();
							// 通过代理模拟获得了connection执行操作
							connection.commit();
						} catch (SQLException e) {
							e.printStackTrace();
						} finally {
							pool.releaseConnection(connection);
							got.incrementAndGet();
						}
					} else {
						notGot.incrementAndGet();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					count--;
				}
			}
			// 每个线程自己执行完操作之后将end减少
			end.countDown();
		}
	}
}
