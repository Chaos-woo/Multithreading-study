package com.chao.concurrency.chapter8;

public class DeadLock {
	private OtherService otherService;

	private static final Object LOCK = new Object();

	public DeadLock(OtherService otherService){
		this.otherService = otherService;
	}

	public void d1(){
		synchronized (LOCK){
			System.out.println("d1===========");
			otherService.o2();
		}
	}

	public void d2(){
		synchronized (LOCK){
			System.out.println("d1===========");
		}
	}

}
