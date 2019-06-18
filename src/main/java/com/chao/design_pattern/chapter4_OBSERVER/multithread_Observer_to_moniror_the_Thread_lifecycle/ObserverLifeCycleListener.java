package com.chao.design_pattern.chapter4_OBSERVER.multithread_Observer_to_moniror_the_Thread_lifecycle;

import java.util.List;

/**
 * 线程生命周期的监管者
 *
 */
public class ObserverLifeCycleListener implements LifeCycleListener {
	private final Object LOCK = new Object();

	// 为每个线程注册监听者和安排任务
	public void concurrentQuery(List<String> ids) {
		if (ids == null || ids.isEmpty()) {
			return;
		}
		ids.forEach(id -> new Thread(new ObservableRunnable(this) {
			@Override
			public void run() {
				try {
					notifyChange(new RunnableEvent(RunnableStatus.RUNNING, Thread.currentThread(), null));
					System.out.println("Query for the id is " + id);
					Thread.sleep(1000);
//					int i = 1/0; error status
					notifyChange(new RunnableEvent(RunnableStatus.DONE, Thread.currentThread(), null));
				} catch (Exception e) {
					notifyChange(new RunnableEvent(RunnableStatus.ERROR, Thread.currentThread(), e));
				}
			}
		}, id).start());
	}

	// 监管者被通知后，可以通过“主题”传递的信息做一些操作
	@Override
	public void onEvent(ObservableRunnable.RunnableEvent event) {
		synchronized (LOCK) {
			System.out.println("The runnable [" + event.getThread().getName() + "] data changed and status is " + event.getStatus());
			if (event.getCause() != null) {
				System.out.println("The runnable [" + event.getThread().getName() + "] process failed.");
				event.getCause().printStackTrace();
			}
		}
	}
}
