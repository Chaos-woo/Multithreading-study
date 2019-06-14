package com.chao.concurrency.chapter9;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 需求：多台机器进行数据采集，使用1个线程代表1个机器，
 *		 同时运行的机器不能超过5个，当其中某一个机器结束工作了，
 *		 剩余的其他机器能够开始工作，直到所有机器完成工作，
 *		 结束本次任务
 * 要求：综合使用生产者-消费者模型和Java8语法糖
 */

public class CaptureService {
	private static final LinkedList<Control> CONTROLS = new LinkedList<>();
	private static final int MAX_MACHINE_COUNT = 5;

	public static void main(String[] args) {
		ArrayList<Thread> machines = new ArrayList<>();

		Stream.of("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9", "M10", "M11", "M12")
				.map(CaptureService::createWorkThread)
				.forEach(t->{
					t.start();
					machines.add(t);
				});

		machines.forEach(t->{
			try {
				// 让main线程等待所有机器工作执行完成
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	private static Thread createWorkThread(String name) {
		return new Thread(() -> {
			// 具体工作内容
			synchronized (CONTROLS) {
				while (CONTROLS.size() > MAX_MACHINE_COUNT) {
					try {
						CONTROLS.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				CONTROLS.addLast(new Control());
			}

			Optional.of("The machine [" + Thread.currentThread().getName() + "] BEGIN to work")
					.ifPresent(System.out::println);
			Optional.of("The machine [" + Thread.currentThread().getName() + "] is working")
					.ifPresent(System.out::println);

			// 模拟工作
			try {
				Thread.sleep(10_000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 工作完成
			synchronized (CONTROLS) {
				CONTROLS.removeFirst();
				// 唤醒其他正在等待的机器
				CONTROLS.notifyAll();
				Optional.of("The machine [" + Thread.currentThread().getName() + "] END work")
						.ifPresent(System.out::println);
			}
		}, name);
	}

	private static class Control {
	}

}
