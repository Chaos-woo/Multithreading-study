package com.chao.design_pattern.chapter1;

/**
 * 不能够懒加载，因为类加载时instance同时被加载到内存
 */
public class SingletonObject1 {
	private static final SingletonObject1 instance = new SingletonObject1();

	private SingletonObject1(){}

	public static SingletonObject1 getInstance(){
		return instance;
	}

}
