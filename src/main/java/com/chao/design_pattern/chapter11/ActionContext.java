package com.chao.design_pattern.chapter11;


/**
 * 利用ThreadLocal的键值对映射，将线程及其对应的值保存起来，
 * 需要时直接通过ThreadLocal直接获取线程对应的值，
 * 而不需要一个线程在执行多个对象的方法（多个方法需要的参数相同）时传参，
 * 这样可以有效地将线程隔离开来，互不干扰取值
 */
public class ActionContext {
	public static final ThreadLocal<Context> threadLocal = new ThreadLocal<Context>(){
		@Override
		public void set(Context context) {
			super.set(context);
		}
	};

	private ActionContext(){}

	private static class ContextHolder{
		private static ActionContext actionContext = new ActionContext();
	}

	public static ActionContext getActionContext(){
		return ContextHolder.actionContext;
	}

	public Context getContext(){
		return threadLocal.get();
	}

}
