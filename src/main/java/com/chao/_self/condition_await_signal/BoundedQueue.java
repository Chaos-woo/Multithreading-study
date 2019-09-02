package com.chao._self.condition_await_signal;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description: 构造一个有界队列，线程安全地获取锁并添加到队列中，
 * 当队列满时，需要添加元素的线程将被阻塞，直到队列有“空位”时线程被唤醒，
 * 继续尝试添加元素到队列中
 *
 * @author W.Chao
 * @date 2019/8/31 9:57
 **/
public class BoundedQueue<T> {
	private Object[] items;

	private int addIndex, removeIndex, count;

	private Lock lock = new ReentrantLock();
	private Condition notFull = lock.newCondition();
	private Condition notEmpty = lock.newCondition();

	public BoundedQueue(int size) {
		items = new Object[size];
	}

	public void add(T t) throws InterruptedException {
		lock.lock();
		// 获取到锁之后尝试添加元素
		try {
			while (count == items.length) {
				notFull.await();
			}
			items[addIndex] = t;
			// 添加的元素为队列最后一个元素时，将添加的下标置为0，循环队列
			/*addIndex = (addIndex+1)%items.length*/
			if (++addIndex == items.length) {
				addIndex = 0;
			}
			count++;
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	public T remove() throws InterruptedException {
		lock.lock();
		try {
			while (count == 0) {
				notEmpty.await();
			}
			Object x = items[removeIndex];
			if (++removeIndex == items.length) {
				removeIndex = 0;
			}
			count--;
			notFull.signal();
			return (T) x;
		} finally {
			lock.unlock();
		}
	}







}
