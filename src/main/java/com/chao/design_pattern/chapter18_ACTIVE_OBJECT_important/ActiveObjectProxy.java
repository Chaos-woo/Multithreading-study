package com.chao.design_pattern.chapter18_ACTIVE_OBJECT_important;

class ActiveObjectProxy implements ActiveObject {
	private final SchedulerThread schedulerThread;
	private final Servant servant;

	public ActiveObjectProxy(SchedulerThread schedulerThread, Servant servant) {
		this.schedulerThread = schedulerThread;
		this.servant = servant;
	}

	/**
	 * 两个方法调用都将被包装为方法请求对象
	 * 立即返回FutureResult对象，并行地执行
	 */
	@Override
	public Result makeString(int count, char fillChar) {
		FutureResult futureResult = new FutureResult();
		schedulerThread.invoke(new MakeStringRequest(servant, futureResult, count, fillChar));
		return futureResult;
	}

	@Override
	public void displayString(String text) {
		this.schedulerThread.invoke(new DisplayStringRequest(servant, text));
	}
}
