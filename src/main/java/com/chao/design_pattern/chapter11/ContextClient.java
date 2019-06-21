package com.chao.design_pattern.chapter11;

import java.util.stream.IntStream;

public class ContextClient {

	public static void main(String[] args) {
		IntStream.range(1,5).forEach(i->{
			Context context = new Context();
			ExecutionTask executionTask = new ExecutionTask(context);
			new Thread(()->{
				executionTask.run();
				System.out.println(context.getName());
				System.out.println(context.getCardId());
			}).start();
		});

	}
}
