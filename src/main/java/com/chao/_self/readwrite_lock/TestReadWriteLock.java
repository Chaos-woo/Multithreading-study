package com.chao._self.readwrite_lock;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

/**
 * Description:
 *
 * @author W.Chao
 * @date 2019/8/30 16:41
 **/
public class TestReadWriteLock {
	static final class Cache {
		private static HashMap<String, String> cache = new HashMap<>();
		private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
		private static Lock r = rwl.readLock();
		private static Lock w = rwl.writeLock();

		public static Object get(String key) {
			r.lock();
			try {
				String result = cache.get(key);
				if (result == null) {
					return "error";
				} else {
					return result;
				}
			} finally {
				r.unlock();
			}
		}

		public static Object put(String key, String value) {
			w.lock();
			try {
				return cache.put(key, value);
			} finally {
				w.unlock();
			}
		}

		public static void clear() {
			w.lock();
			try {
				cache.clear();
			} finally {
				w.unlock();
			}
		}
	}

	static class WriteWorker extends Thread {
		private int count;

		public WriteWorker(String name, int count) {
			super(name);
			this.count = count;
		}

		@Override
		public void run() {
			while (true) {
				try {
					count++;
					System.out.println(Thread.currentThread().getName() + "->" + count);
					Cache.put("1", String.valueOf(count));
					TimeUnit.SECONDS.sleep(5);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	static class ReadWorker extends Thread {
		public ReadWorker(String name) {
			super(name);
		}

		@Override
		public void run() {
			while (true) {
				try {
					System.out.println(Thread.currentThread().getName()+"->"+Cache.get("1"));
					TimeUnit.SECONDS.sleep(3);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {

		new WriteWorker("Write-Thread-0",0).start();

		IntStream.range(1, 5).forEach(i -> {
			new ReadWorker("Read-Thread-" + i).start();
		});
	}

}
