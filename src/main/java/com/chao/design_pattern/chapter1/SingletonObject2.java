package com.chao.design_pattern.chapter1;

/**
 * 多线程情况下可能会出现不止一个实例
 */

public class SingletonObject2 {

	private static SingletonObject2 instance;

	private SingletonObject2(){}

	public static SingletonObject2 getInstance(){
		if(null == instance){
			instance = new SingletonObject2();
		}
		return SingletonObject2.instance;
	}
}
