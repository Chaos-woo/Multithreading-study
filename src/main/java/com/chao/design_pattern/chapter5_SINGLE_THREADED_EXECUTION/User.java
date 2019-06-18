package com.chao.design_pattern.chapter5_SINGLE_THREADED_EXECUTION;

public class User extends Thread {
	private Gate gate;
	private String name;
	private String address;

	public User(Gate gate, String name, String address) {
		this.gate = gate;
		this.name = name;
		this.address = address;
	}

	@Override
	public void run() {
		int i = 0;
		while (true) {
			gate.pass(name, address);
			++i;
		}
	}
}
