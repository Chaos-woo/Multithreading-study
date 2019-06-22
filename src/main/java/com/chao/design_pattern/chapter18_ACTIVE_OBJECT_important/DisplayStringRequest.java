package com.chao.design_pattern.chapter18_ACTIVE_OBJECT_important;

/**
 * {@link ActiveObject#displayString(String)}
 * displayString()的方法请求对象
 */
public class DisplayStringRequest extends MethodRequest {
	private final String text;

	protected DisplayStringRequest(Servant servant, String text) {
		super(servant, null);
		this.text = text;
	}

	@Override
	public void execute() {
		this.servant.displayString(text);
	}
}
