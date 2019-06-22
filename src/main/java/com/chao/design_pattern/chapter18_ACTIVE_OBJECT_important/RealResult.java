package com.chao.design_pattern.chapter18_ACTIVE_OBJECT_important;

/**
 * 真正的结果对象，放置于FutureResult对象中，
 * 等待被获取结果值
 */
public class RealResult implements Result {

	private final Object resultValue;

	public RealResult(final Object resultValue){
		this.resultValue = resultValue;
	}

	@Override
	public Object getResultValue() {
		return resultValue;
	}
}
