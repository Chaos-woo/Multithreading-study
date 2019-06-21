package com.chao.design_pattern.chapter9;

import java.util.LinkedList;

public class RequestQueue {
	private static final LinkedList<Request> requestLinkedList = new LinkedList<>();

	public Request popRequest(){
		synchronized (requestLinkedList){
			if(requestLinkedList.size() <= 0){
				try {
					requestLinkedList.wait();
				} catch (InterruptedException e) {
					// 线程被中断则直接返回null，代表没有取到请求
					return null;
				}
			}
			return requestLinkedList.removeFirst();
		}
	}

	public void pushRequest(Request request){
		synchronized (requestLinkedList){
			requestLinkedList.addLast(request);
			requestLinkedList.notifyAll();
		}
	}

}
