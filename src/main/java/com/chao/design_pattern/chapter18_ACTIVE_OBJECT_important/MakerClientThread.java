package com.chao.design_pattern.chapter18_ACTIVE_OBJECT_important;

public class MakerClientThread extends Thread {
	private final ActiveObject activeObject;
	private final char fillChar;

	public MakerClientThread(String name, ActiveObject activeObject) {
		super(name);
		this.activeObject = activeObject;
		this.fillChar = name.charAt(0);
	}

	@Override
	public void run() {
		try {
			for (int i = 0; true; i++) {
				/**
				 * 通过proxy进行方法调用
				 * 返回的result是FutureResult对象
				 */
				Result result = activeObject.makeString(i + 1, fillChar);
				Thread.sleep(100);
				String value = (String) result.getResultValue();
				System.out.println(Thread.currentThread().getName() + ":value=" + value);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
