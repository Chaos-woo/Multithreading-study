package com.chao.design_pattern.chapter11;

public class ExecutionTask implements Runnable{
	private Context context;

	public ExecutionTask(Context context) {
		this.context = context;
	}

	@Override
	public void run() {
		ActionContext.threadLocal.set(context);
		QueryFromDBAction queryFromDBAction = new QueryFromDBAction();
		queryFromDBAction.execute();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		QueryFromHttp queryFromHttp = new QueryFromHttp();
		queryFromHttp.execute();
	}
}
