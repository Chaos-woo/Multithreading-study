package com.chao.design_pattern.chapter6_READ_WRITE_LOCK;

	/*是否进行串行化（加锁）处理[y/n?]
			+-------------------------+
			+      |READ  |   WRITE	  +
			+-------------------------+
			+ READ |  N   |     Y     +
			+-------------------------+
			+WRITE |  Y   |     Y     +
			+-------------------------+*/

public class ReadWriteLock {
	// 正在读取数据的线程数量
	private static int readingReader = 0;
	// 等待读取数据的线程数量
	private static int waitingReader = 0;
	// 正在写入数据的线程数量
	private static int writingWriter = 0;
	// 等待写入数据的线程数量
	private static int waitingWriter = 0;
	// 对读写操作的偏好设置
	private boolean prefWrite;

	public ReadWriteLock(boolean prefWrite) {
		this.prefWrite = prefWrite;
	}

	public synchronized void readLock() throws InterruptedException {
		try {
			waitingReader++;
			// 有线程正在对数据写入时，不可读取数据
			while (writingWriter > 0 || (prefWrite && writingWriter > 0)) {
				wait();
			}
			readingReader++;
		} finally {
			waitingReader--;
		}
	}

	public synchronized void readUnlock() {
		readingReader--;
		notifyAll();
	}

	public synchronized void writeLock() throws InterruptedException {
		try {
			waitingWriter++;
			// 有线程正在等待读取或有其他线程正在写入数据时，不可对数据进行写操作
			while (waitingReader > 0 || readingReader > 0) {
				wait();
			}
		} finally {
			waitingWriter--;
		}
	}

	public synchronized void writeUnlock() {
		writingWriter--;
		notifyAll();
	}

}
