package com.chao.design_pattern.chapter1;

/**
 * 解决了性能问题
 *
 * 可能会出现NullPointException
 */

public class SingletonObject4 {

	private static SingletonObject4 instance;
//	private Object obj;

	private SingletonObject4(){
		// do some heavy things
		// int i=0, j=10;
		// obj = new Object();
		// ...
	}

	public static SingletonObject4 getInstance(){
		if(null == instance){
			synchronized (SingletonObject4.class){
				if(null == instance){
					instance = new SingletonObject4();
				}
			}
		}
		return SingletonObject4.instance;
	}
}
