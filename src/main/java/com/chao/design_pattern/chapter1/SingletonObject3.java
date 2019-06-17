package com.chao.design_pattern.chapter1;

/**
 * 解决了懒加载、多实例的问题
 * 但是除了第一个线程外是创建实例，其他线程都是读的操作，
 * 都需要等待前一个线程完成读操作，影响性能
 */

public class SingletonObject3 {

	private static SingletonObject3 instance;

	private SingletonObject3(){}

	public synchronized static SingletonObject3 getInstance(){
		if(null == instance){
			instance = new SingletonObject3();
		}
		return SingletonObject3.instance;
	}
}
