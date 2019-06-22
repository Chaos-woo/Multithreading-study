package com.chao.design_pattern.chapter18_ACTIVE_OBJECT_important;

/**
 * 方法调用的真正执行者线程，
 * 实现了ActiveObject中的方法
 */
class Servant implements ActiveObject {
	@Override
	public Result makeString(int count, char fillChar) {
		char[] buf = new char[count];
		for (int i = 0; i < count; i++) {
			buf[i] = fillChar;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return new RealResult(new String(buf));
	}

	@Override
	public void displayString(String text) {
		try {
			System.out.println("Display: " + text);
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
