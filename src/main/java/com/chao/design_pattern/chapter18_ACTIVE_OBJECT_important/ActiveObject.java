package com.chao.design_pattern.chapter18_ACTIVE_OBJECT_important;

/**
 * 主动对象模式
 * 常用应用场景：一般的异步方法调用只能调用一个方法
 * 使用Active Object可以调用多个方法，即一个方法执行之后，还可以执行其他方法
 * 而不会像一般异步方法在一个方法体中调用其他方法
 */
public interface ActiveObject {
	Result makeString(int count, char fillChar);
	void displayString(String text);
}
