package com.chao._self.cyclicBarrier;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Description:
 *
 * @author W.Chao
 * @date 2019/9/2 18:36
 **/
public class BankWaterService implements Runnable {
	private CyclicBarrier cyclicBarrier = new CyclicBarrier(4, this);

	private Executor executor = Executors.newFixedThreadPool(4);

	private ConcurrentHashMap<String, Integer> sheetBankWaterCount = new ConcurrentHashMap<>();

	private void count() {
		for (int i = 0; i < 4; i++) {
			// 启动4个线程去模拟计算
			executor.execute(() -> {
				sheetBankWaterCount.put(Thread.currentThread().getName(), 1);
				// 计算完成之后调用await()方法阻塞
				try {
					cyclicBarrier.await();
					System.out.println(Thread.currentThread().getName());
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			});
		}
	}

	@Override
	public void run() {
		int result = 0;
		for (Map.Entry<String, Integer> sheet : sheetBankWaterCount.entrySet()) {
			result += sheet.getValue();
		}
		sheetBankWaterCount.put("result", result);
		System.out.println(result);
	}

	public static void main(String[] args) {
		BankWaterService bankWaterService = new BankWaterService();
		bankWaterService.count();
	}
}
