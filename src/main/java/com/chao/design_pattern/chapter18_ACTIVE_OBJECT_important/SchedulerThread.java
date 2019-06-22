package com.chao.design_pattern.chapter18_ACTIVE_OBJECT_important;

/**
 * 方法请求对象执行的工作者线程，
 * 该线程与主线程（或其他方法调用线程）不属于同一线程，
 * 并行地执行方法请求
 */
public class SchedulerThread extends Thread{

	private final ActivationQueue activationQueue;

	public SchedulerThread(ActivationQueue activationQueue) {
		this.activationQueue = activationQueue;
	}

	public void invoke(MethodRequest request){
		this.activationQueue.put(request);
	}

	@Override
	public void run() {
		while (true){
			try {
				Thread.sleep(1_000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			activationQueue.take().execute();
		}
	}
}
