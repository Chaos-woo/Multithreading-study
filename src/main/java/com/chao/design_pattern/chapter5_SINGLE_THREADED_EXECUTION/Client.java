package com.chao.design_pattern.chapter5_SINGLE_THREADED_EXECUTION;

public class Client {
	public static void main(String[] args) {
		Gate gate = new Gate();
		User bj = new User(gate, "Beijing", "Beijing");
		User sh = new User(gate, "Shanghai", "Shanghai");
		User gz = new User(gate, "Guangzhou", "Guangzhou");

		bj.start();
		sh.start();
		gz.start();
	}
}
