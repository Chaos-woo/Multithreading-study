package com.chao.concurrency.chapter10_SELF_LOCK;

import java.util.Collection;

public interface Lock {

	class TimeoutException extends Exception{
		TimeoutException(String msg){
			super(msg);
		}
	}

	void lock();

	void lock(long mills) throws TimeoutException;

	void unlock();

	Collection<Thread> getBlockedCollection();

	int getCollectionOfBlockedThreadSize();

}
