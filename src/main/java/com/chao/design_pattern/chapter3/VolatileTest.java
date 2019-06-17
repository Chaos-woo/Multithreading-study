package com.chao.design_pattern.chapter3;

/**
 * volatile：保证内存可见性和有序性
 */
public class VolatileTest {
	private volatile static int INIT_VALUE = 0;

	private final static int MAX_LIMIT = 5;

	public static void main(String[] args) {
		new Thread(()->{
			int localValue = INIT_VALUE;
			while (localValue < MAX_LIMIT){
				if(localValue != INIT_VALUE){
					System.out.printf("The value is [%d]\n",INIT_VALUE);
					localValue = INIT_VALUE;
				}
			}
		},"READER").start();

		new Thread(()->{
			int localValue = INIT_VALUE;
			while (INIT_VALUE < MAX_LIMIT){
				if(localValue == INIT_VALUE){
					System.out.printf("Update the value to [%d]\n",++localValue);
					INIT_VALUE = localValue;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		},"WRITER").start();
	}
}
