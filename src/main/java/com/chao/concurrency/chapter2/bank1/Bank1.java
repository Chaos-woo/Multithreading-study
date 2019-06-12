package com.chao.concurrency.chapter2.bank1;

public class Bank1 {
	public static void main(String[] args) {
		TicketWindowThread t1 = new TicketWindowThread("1号柜台");
		TicketWindowThread t2 = new TicketWindowThread("2号柜台");
		TicketWindowThread t3 = new TicketWindowThread("3号柜台");

		t1.start();
		t2.start();
		t3.start();
	}
}
