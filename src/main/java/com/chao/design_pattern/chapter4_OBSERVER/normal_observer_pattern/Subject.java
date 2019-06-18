package com.chao.design_pattern.chapter4_OBSERVER.normal_observer_pattern;

import java.util.ArrayList;

public class Subject {
	private int status;

	private ArrayList<Observer> observers = new ArrayList<>();

	public int getStatus() {
		return status;
	}

	public void attach(Observer observer){
		observers.add(observer);
	}

	public void setStatus(int status) {
		if(this.status == status){
			return;
		}
		this.status = status;
		notifyAllObservers();
	}

	private void notifyAllObservers() {
		observers.forEach(Observer::update);
	}


}
