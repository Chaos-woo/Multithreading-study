package com.chao.design_pattern.chapter18_ACTIVE_OBJECT_important;


/**
 * {@link ActiveObject#makeString(int, char)}
 * makeString()的方法请求对象
 */
public class MakeStringRequest extends MethodRequest {
	private final int count;
	private final char fillChar;

	protected MakeStringRequest(Servant servant, FutureResult futureResult, int count, char fillChar) {
		super(servant, futureResult);
		this.count = count;
		this.fillChar = fillChar;
	}

	@Override
	public void execute() {
		Result result = this.servant.makeString(count,fillChar );
		this.futureResult.setResult(result);
	}
}
