package com.chao.design_pattern.chapter13_PRODUCER_CONSUMER;

public class Message {
	private String msg;

	public Message(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
}
