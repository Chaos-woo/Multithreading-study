package com.chao.design_pattern.chapter18_ACTIVE_OBJECT_important;

public abstract class MethodRequest {

	protected final Servant servant;
	protected final FutureResult futureResult;

	protected MethodRequest(Servant servant, FutureResult futureResult) {
		this.servant = servant;
		this.futureResult = futureResult;
	}
	public abstract void execute();

}
