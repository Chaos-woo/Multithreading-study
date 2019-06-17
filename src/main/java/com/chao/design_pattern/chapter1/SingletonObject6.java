package com.chao.design_pattern.chapter1;


/**
 * 静态内部类中的静态变量Instance在类加载初始化阶段就已经创建好了
 */
public class SingletonObject6 {
	private SingletonObject6(){}

	private static class InstanceHolder{
		private static final SingletonObject6 Instance = new SingletonObject6();
	}

	public static SingletonObject6 getInstance(){
		return InstanceHolder.Instance;
	}
}
