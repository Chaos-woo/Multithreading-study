package com.chao.design_pattern.chapter18_ACTIVE_OBJECT_important;

import java.util.LinkedList;

/**
 * 方法请求对象的缓冲区
 * 使用生产者-消费者模式
 * 再进行存取时需要锁串行执行
 */
public class ActivationQueue {

	private final static int MAX_METHOD_REQUEST_SIZE = 100;

	private final LinkedList<MethodRequest> methodRequests = new LinkedList<>();


	public synchronized void put(MethodRequest request){
		while (methodRequests.size() >= MAX_METHOD_REQUEST_SIZE){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.methodRequests.addLast(request);
		this.notifyAll();
	}

	public synchronized MethodRequest take(){
		while (methodRequests.isEmpty()){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		MethodRequest request = methodRequests.removeFirst();
		this.notifyAll();
		return request;
	}
}
