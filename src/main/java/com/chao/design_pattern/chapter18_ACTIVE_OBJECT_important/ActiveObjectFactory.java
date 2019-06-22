package com.chao.design_pattern.chapter18_ACTIVE_OBJECT_important;

public class ActiveObjectFactory {
	private ActiveObjectFactory() {
	}

	/**
	 * 创建上下文信息
	 * @return 返回proxy对象
	 */
	public static ActiveObject createActiveObject() {
		Servant servant = new Servant();
		ActivationQueue activationQueue = new ActivationQueue();
		SchedulerThread schedulerThread = new SchedulerThread(activationQueue);
		ActiveObjectProxy activeObjectProxy = new ActiveObjectProxy(schedulerThread, servant);
		// 工作者线程开启
		schedulerThread.start();
		return activeObjectProxy;
	}

}
