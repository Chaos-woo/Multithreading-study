package com.chao.design_pattern.chapter5_SINGLE_THREADED_EXECUTION;

public class Gate {
	private int count;
	private String name;
	private String address;

	/**
	 * sharedResource
	 * add a lock
	 *
	 * @param name
	 * @param address
	 */
	public synchronized void pass(String name, String address) {
		count++;
		this.name = name;
		this.address = address;
		verify();
	}

	private void verify() {
		if (name.charAt(0) != address.charAt(0)) {
			System.out.println("*******BROKEN*******" + toString());
		}
	}

	// sharedResource
	public synchronized String toString() {
		return "No." + count + " " + this.name + " " + this.address;
	}
}
