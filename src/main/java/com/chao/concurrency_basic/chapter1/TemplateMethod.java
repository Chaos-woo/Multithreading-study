package com.chao.concurrency_basic.chapter1;

public class TemplateMethod {

	public final void templateMethod(){
		System.out.println("******************************");
		print();
		System.out.println("******************************");
	}

	protected void print(){
		// override this method
	}

	public static void main(String[] args) {
		TemplateMethod t = new TemplateMethod(){
			@Override
			protected void print() {
				System.out.println("** template method **");
			}
		};
		t.templateMethod();
	}
}
