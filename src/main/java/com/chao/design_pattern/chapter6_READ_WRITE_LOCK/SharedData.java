package com.chao.design_pattern.chapter6_READ_WRITE_LOCK;

/**
 * 共享数据类：临界区
 * 该类中的读、写操作都是线程间遵守读写锁规则的操作，
 * 这样可以实现读操作的并行，写操作串行，提高效率
 */
public class SharedData {
	private final char[] buffer;
	private ReadWriteLock lock;

	public SharedData(int size){
		this(size,true);
	}

	public SharedData(int size, boolean prefWrite) {
		this.lock = new ReadWriteLock(prefWrite);
		this.buffer = new char[size];
		for (int i = 0; i < size; i++) {
			buffer[i] = '*';
		}
	}

	public String read() throws InterruptedException {
		lock.readLock();
		String buf = doRead();
		slowly(100);
		lock.readUnlock();
		return buf;
	}

	private void slowly(int mills) {
		try {
			Thread.sleep(mills);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String doRead() {
		char[] newBuf = new char[buffer.length];
		System.arraycopy(buffer, 0, newBuf, 0, buffer.length);
		return String.valueOf(newBuf);
	}

	public void write(char c) throws InterruptedException {
		lock.writeLock();
		doWrite(c);
		slowly(5);
		lock.writeUnlock();
	}

	private void doWrite(char c) {
		for(int i=0;i<buffer.length;i++){
			buffer[i] = c;
		}
	}


}
