package com.chao.design_pattern.chapter7_IMMUTABLE_DESIGN_PATTERN;

public final class Person {
	private final String name;
	private final String address;

	public Person(String name, String address) {
		this.name = name;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				", address='" + address + '\'' +
				'}';
	}
}
