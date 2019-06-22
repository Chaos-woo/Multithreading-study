package com.chao.design_pattern.chapter18_ACTIVE_OBJECT_important;

public class FutureResult implements Result {

	private Result result;
	private boolean ready = false;

	public synchronized void setResult(Result result){
		this.ready = true;
		this.result = result;
		this.notifyAll();
	}

	/**
	 * 当其他线程需要获取结果时，
	 * 如若结果未准备好，需要等待，
	 * 并等待其他线程的唤醒
	 */
	@Override
	public synchronized Object getResultValue() {
		while (!ready){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return this.result.getResultValue();
	}
}
