package com.chao.design_pattern.chapter18_ACTIVE_OBJECT_important;

public class Client {
	public static void main(String[] args) {
		ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
		// 两个MakeString的方法调用者
		new MakerClientThread("Alice", activeObject).start();
		new MakerClientThread("Bobby", activeObject).start();

		// DisplayString的方法调用者
		new DisplayClientThread("Chris", activeObject).start();
	}
}
