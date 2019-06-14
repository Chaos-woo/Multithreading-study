package com.chao.concurrency.chapter10_SELF_LOCK;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;

public class BooleanLock implements Lock{
	private LinkedList<Thread> CollectionOfBlockedThread = new LinkedList<>();

	// lockValue is true meant a thread get the lock
	// lockValue is false meant the lock is free. (any threads can get the lock)
	private boolean lockValue;
	private Thread currentThread;

	@Override
	public synchronized void lock(){
		while (lockValue){
			try {
				// add all blocked thread
				CollectionOfBlockedThread.addLast(Thread.currentThread());
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.lockValue = true;
		CollectionOfBlockedThread.clear();
		this.currentThread = Thread.currentThread();
	}

	@Override
	public synchronized void lock(long mills) throws TimeoutException {
		if(mills <= 0){
			lock();
		}
		long endTime = System.currentTimeMillis() + mills;
		long hasRemaining = endTime - System.currentTimeMillis();
		while (lockValue){
			if(hasRemaining <= 0){
				throw new TimeoutException("Timeout Exception");
			}else {
				try {
					CollectionOfBlockedThread.addLast(Thread.currentThread());
					this.wait(mills);
					hasRemaining = endTime - System.currentTimeMillis();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		this.lockValue = true;
		CollectionOfBlockedThread.clear();
		this.currentThread = Thread.currentThread();
	}

	@Override
	public synchronized void unlock() {
		if(currentThread == Thread.currentThread()){
			Optional.of(Thread.currentThread().getName() + " release the lock")
					.ifPresent(System.out::println);
			lockValue = false;
			notifyAll();
		}
	}

	@Override
	public Collection<Thread> getBlockedCollection() {
		return Collections.unmodifiableCollection(CollectionOfBlockedThread);
	}

	@Override
	public int getCollectionOfBlockedThreadSize() {
		return CollectionOfBlockedThread.size();
	}
}
