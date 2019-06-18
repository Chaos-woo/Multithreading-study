package com.chao.design_pattern.chapter7_IMMUTABLE_DESIGN_PATTERN;

public class UserPersonThread extends Thread {
	private Person person;

	public UserPersonThread(Person person){
		this.person = person;
	}

	@Override
	public void run() {
		while (true){
			System.out.println("The thread " + Thread.currentThread().getName() + " " + person.getName() + " " + person.getAddress());
		}
	}
}
