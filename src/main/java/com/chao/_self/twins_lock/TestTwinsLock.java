package com.chao._self.twins_lock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * Description:
 *
 * @author W.Chao
 * @date 2019/8/30 14:32
 **/
public class TestTwinsLock {
	public static void main(String[] args) {
		final Lock twinsLock = new TwinsLock();
		class Worker extends Thread{
			public Worker(String name){
				super(name);
			}

			@Override
			public void run() {
				while (true){
					twinsLock.lock();
					try{
						TimeUnit.SECONDS.sleep(1);
						System.out.println(Thread.currentThread().getName());
						TimeUnit.SECONDS.sleep(1);
					}catch(Exception e){
						e.printStackTrace();
					}finally {
						twinsLock.unlock();
					}
				}
			}
		}

		IntStream.range(0, 10).forEach(i->{
			Worker w = new Worker("TwinsLock-"+i);
			w.setDaemon(true);
			w.start();
		});

		for (int i=0;i<10;i++){
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println();
		}


		ReentrantLock r = new ReentrantLock();
		r.lock();
	}
}
