package com.chao.design_pattern.chapter4_OBSERVER.multithread_Observer_to_moniror_the_Thread_lifecycle;

import java.util.Arrays;

public class LifeCycleClient {
	public static void main(String[] args) {
		new ObserverLifeCycleListener().concurrentQuery(Arrays.asList("1","2","3"));
	}
}
