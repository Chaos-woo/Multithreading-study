package com.chao._self.connection_pool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * Description:
 *
 * @author W.Chao
 * @date 2019/8/21 16:07
 **/
public class ConnectionPool {
	private final LinkedList<Connection> pool = new LinkedList<>();

	public ConnectionPool(int maxSize) {
		if (maxSize > 0) {
			for (int i = 0; i < 10; i++) {
				pool.addLast(ConnectionDriver.createConnection());
			}
		}
	}

	public void releaseConnection(Connection connection) {
		if (connection != null) {
			synchronized (pool) {
				pool.addLast(connection);
			}
		}
	}

	/**
	 * 获取连接
	 * @param mills
	 * @return
	 * @throws InterruptedException
	 */
	public Connection fetchConnection(long mills) throws InterruptedException {
		synchronized (pool) {
			//完全超时
			if (mills <= 0) {
				while (pool.isEmpty()) {
					pool.wait();
				}
			} else {
				long future = System.currentTimeMillis() + mills;
				long remaining = mills;
				// 连接池空且还有等待的时间，线程继续等待
				while (pool.isEmpty() && remaining > 0) {
					pool.wait(remaining);
					// 等待线程被其他线程唤醒之后要重新计算还能等待的时间
					remaining = future - System.currentTimeMillis();
				}
				Connection result = null;
				if (!pool.isEmpty()) {
					result = pool.removeFirst();
				}
				return result;
			}
		}
		return null;
	}


}
