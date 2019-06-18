package com.chao.design_pattern.chapter4_OBSERVER.multithread_Observer_to_moniror_the_Thread_lifecycle;

/**
 * 被监控的线程对象：主题
 */
public abstract class ObservableRunnable implements Runnable{
	private ObserverLifeCycleListener listener;

	// 注册监管者
	public ObservableRunnable(ObserverLifeCycleListener listener) {
		this.listener = listener;
	}

	// 使用监管者的onEvent()方法来进行通知
	public void notifyChange(RunnableEvent event){
		listener.onEvent(event);
	}

	public enum RunnableStatus{
		RUNNING,ERROR,DONE
	}

	/**
	 * 线程事件
	 * 包括线程的状态、当前线程、线程执行时发生的错误
	 */
	public static class RunnableEvent{
		private final RunnableStatus status;
		private final Thread thread;
		private final Throwable cause;

		public RunnableEvent(RunnableStatus status, Thread thread, Throwable cause) {
			this.status = status;
			this.thread = thread;
			this.cause = cause;
		}

		public RunnableStatus getStatus() {
			return status;
		}

		public Thread getThread() {
			return thread;
		}

		public Throwable getCause() {
			return cause;
		}
	}

}
