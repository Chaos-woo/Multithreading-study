package com.chao.design_pattern.chapter17_WORKER_THREAD;

import java.util.Arrays;
import java.util.LinkedList;

public class Channel {
	private LinkedList<Request> RequestQueue;
	private WorkerTread[] workerThreads;
	private int workerCount;
	private int queueMaxCount;

	public Channel(int workerCount) {
		this.workerCount = workerCount;
		this.RequestQueue = new LinkedList<>();
		this.workerThreads = new WorkerTread[workerCount];
		this.queueMaxCount = 100;
		init();
	}

	private void init() {
		for (int i = 0; i < workerCount; i++) {
			workerThreads[i] = new WorkerTread("Worker Thread-" + i, this);
		}
	}

	public void startWork() {
		Arrays.asList(workerThreads).forEach(WorkerTread::start);
	}

	public synchronized void put(Request request){
		while (RequestQueue.size() >= queueMaxCount){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		RequestQueue.addLast(request);
		this.notifyAll();
	}

	public synchronized Request take(){
		while (RequestQueue.isEmpty()){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Request request = RequestQueue.removeFirst();
		this.notifyAll();
		return request;
	}


}
