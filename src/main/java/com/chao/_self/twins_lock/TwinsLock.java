package com.chao._self.twins_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Description:设计一个同步工具：该工具在同一时刻，只允许至多两个线程同时访问，超过两个线程的
 * 访问将被阻塞
 *
 * @author W.Chao
 * @date 2019/8/30 14:31
 **/
public class TwinsLock implements Lock {
	private static final Sync sync = new Sync(2);

	private static final class Sync extends AbstractQueuedSynchronizer {
		Sync(int count) {
			if (count <= 0) {
				throw new IllegalArgumentException("count must large than zero");
			}
			setState(count);
		}

		@Override
		protected int tryAcquireShared(int reduceCount) {
			for (; ; ) {
				int current = getState();
				int newCount = current - reduceCount;
				if (newCount < 0 || compareAndSetState(current, newCount)) {
					return newCount;
				}
			}
		}

		@Override
		protected boolean tryReleaseShared(int returnCount) {
			for (; ; ) {
				int current = getState();
				int newCount = current + returnCount;
				if (compareAndSetState(current, newCount)) {
					return true;
				}
			}
		}

		@Override
		protected boolean isHeldExclusively() {
			return getState()==1;
		}

		Condition newCondition(){
			return new ConditionObject();
		}
	}

	@Override
	public void lock() {
		sync.acquireShared(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock() {
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}

	@Override
	public void unlock() {
		sync.releaseShared(1);
	}

	@Override
	public Condition newCondition() {
		return sync.newCondition();
	}
}
