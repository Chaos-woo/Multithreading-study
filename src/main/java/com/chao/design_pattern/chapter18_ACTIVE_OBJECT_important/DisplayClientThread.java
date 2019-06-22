package com.chao.design_pattern.chapter18_ACTIVE_OBJECT_important;

public class DisplayClientThread extends Thread {

	private final ActiveObject activeObject;

	public DisplayClientThread(String name, ActiveObject activeObject) {
		super(name);
		this.activeObject = activeObject;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; true; i++) {
				/**
				 * 通过proxy进行方法调用
				 * 返回的result是FutureResult对象
				 */
				String text = Thread.currentThread().getName() + "->" + i;
				activeObject.displayString(text);
				Thread.sleep(200);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
