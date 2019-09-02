package com.chao._self.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Description:
 *
 * @author W.Chao
 * @date 2019/9/2 19:13
 **/
public class ExchangerTest {
	private static final Exchanger<String> exchanger = new Exchanger<>();
	private static Executor executor = Executors.newFixedThreadPool(2);

	public static void main(String[] args) {
		executor.execute(() -> {
			String A = "银行流水A";
			try {
				String exchangeData = exchanger.exchange(null);
				System.out.println("A收到数据是:"+ exchangeData);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		executor.execute(() -> {
			String B = "银行流水B";
			try {
				String exchangeData = exchanger.exchange(B);
				System.out.println("A和B的数据是否一致：" + exchangeData.equals(B) +
						"\ncurrent B = " + B + "\ncurrent A = " + exchangeData);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
}
