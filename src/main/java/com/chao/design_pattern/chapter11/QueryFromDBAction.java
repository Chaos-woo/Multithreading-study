package com.chao.design_pattern.chapter11;

import java.util.Random;

public class QueryFromDBAction {
	private static Random random = new Random(System.currentTimeMillis());
	public void execute(){
		try {
			Thread.sleep(random.nextInt(1000));
			String name = "leo " + Thread.currentThread().getName();
			Context context = ActionContext.getActionContext().getContext();
			context.setName(name);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
