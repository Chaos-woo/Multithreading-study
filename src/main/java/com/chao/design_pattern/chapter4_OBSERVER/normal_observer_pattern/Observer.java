package com.chao.design_pattern.chapter4_OBSERVER.normal_observer_pattern;

public abstract class Observer {
	protected Subject subject;

	public Observer(Subject subject){
		this.subject = subject;
		subject.attach(this);
	}

	public abstract void update();

}
