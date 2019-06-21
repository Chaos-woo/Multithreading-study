package com.chao.design_pattern.chapter8_FUTURE;

public class AsynFuture<T> implements Future<T> {
	private volatile boolean done = false;
	private T result;

	public void done(T result){
		synchronized (this){
			this.result = result;
			// 通知等待取结果的线程可以取走结果
			this.done = true;
			this.notifyAll();
		}
	}

	@Override
	public T get() throws InterruptedException {
		synchronized (this){
			while (!done){
				this.wait();
			}
		}
		return result;
	}
}
