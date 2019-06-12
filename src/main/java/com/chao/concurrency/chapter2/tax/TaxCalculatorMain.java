package com.chao.concurrency.chapter2.tax;

public class TaxCalculatorMain {
	public static void main(String[] args) {
/*		TaxCalculator taxCalculator = new TaxCalculator(10000d,2000d){
			@Override
			protected double calcTax() {
				return getSalary()*0.1 + getBonus()*0.15;
			}
		};

		System.out.println(taxCalculator.calculate());*/


		TaxCalculator taxCalculator = new TaxCalculator(10000d,2000d);
		CalculatorStrategy calculatorStrategy = new SimpleCalculatorStrategy();
		taxCalculator.setCalculatorStrategy(calculatorStrategy);
		double tax = taxCalculator.calculate();
		System.out.println(tax);
	}

}
