package com.chao.concurrency.chapter2.tax;

public class SimpleCalculatorStrategy implements CalculatorStrategy {

	private final static double SALARY_RATE = 0.1;
	private final static double BONUS_RATE = 0.15;

	public double calculate(double salary, double bonus) {
		return salary * SALARY_RATE + bonus * BONUS_RATE;
	}
}
