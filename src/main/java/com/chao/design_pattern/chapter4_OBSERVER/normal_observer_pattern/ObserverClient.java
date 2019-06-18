package com.chao.design_pattern.chapter4_OBSERVER.normal_observer_pattern;

public class ObserverClient {
	public static void main(String[] args) {
		Subject subject = new Subject();
		new BinaryObserver(subject);
		new OctalObserver(subject);
		System.out.println("==============");
		subject.setStatus(10);
		System.out.println("==============");
		subject.setStatus(10);
		System.out.println("==============");
		subject.setStatus(15);
		System.out.println("==============");
	}
}
