package com.chao._self.self_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Description:
 *
 * @author W.Chao
 * @date 2019/8/22 14:48
 **/
public class Mutex implements Lock {
	/**
	 * 推荐使用的子类
	 */
	private static class Sync extends AbstractQueuedSynchronizer {
		// 当状态为0的时候上锁
		@Override
		protected boolean tryAcquire(int arg) {
			if (compareAndSetState(0, 1)) {
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}

		// 释放锁，将状态设置为0
		@Override
		protected boolean tryRelease(int arg) {
			if (getState() == 0) {
				throw new IllegalMonitorStateException();
			}
			setExclusiveOwnerThread(null);
			return true;
		}

		// 是否处于占用状态
		@Override
		protected boolean isHeldExclusively() {
			return getState() == 1;
		}

		Condition newCondition() {
			return new ConditionObject();
		}
	}

	private final Sync sync = new Sync();

	@Override
	public void lock() {
		sync.tryAcquire(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return sync.tryAcquireNanos(1, unit.toNanos(time));
	}

	@Override
	public void unlock() {
		sync.tryRelease(1);
	}

	@Override
	public Condition newCondition() {
		return sync.newCondition();
	}

	public boolean hasQueuedThreads() {
		return sync.hasQueuedThreads();
	}

	public boolean isLocked() {
		return sync.isHeldExclusively();
	}
}
