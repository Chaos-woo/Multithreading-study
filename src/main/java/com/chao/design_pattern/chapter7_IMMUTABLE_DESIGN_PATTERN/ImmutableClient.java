package com.chao.design_pattern.chapter7_IMMUTABLE_DESIGN_PATTERN;

import java.util.stream.IntStream;

public class ImmutableClient {

	public static void main(String[] args) {
		Person person = new Person("leo", "SNH");
		IntStream.range(0, 5).forEach(i -> new UserPersonThread(person).start());
	}
}
