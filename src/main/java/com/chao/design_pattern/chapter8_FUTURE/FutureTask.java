package com.chao.design_pattern.chapter8_FUTURE;

/**
 * 真正执行任务的接口
 * @param <T>
 */
public interface FutureTask<T> {
	T call();
}
