package com.chao.design_pattern.chapter4_OBSERVER.multithread_Observer_to_moniror_the_Thread_lifecycle;

public interface LifeCycleListener {
	void onEvent(ObservableRunnable.RunnableEvent event);
}
