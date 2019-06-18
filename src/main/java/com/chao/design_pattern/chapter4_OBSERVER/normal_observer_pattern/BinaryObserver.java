package com.chao.design_pattern.chapter4_OBSERVER.normal_observer_pattern;

public class BinaryObserver extends Observer {

	public BinaryObserver(Subject subject){
		super(subject);
	}

	@Override
	public void update() {
		System.out.println("Binary Observer: " + Integer.toBinaryString(subject.getStatus()));
	}
}
