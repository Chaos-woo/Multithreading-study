package com.chao._self.thread_pool;

/**
 * Description:
 *
 * @author W.Chao
 * @date 2019/8/21 19:00
 **/
public interface ThreadPool<Job extends Runnable>{
	// 执行job任务
	void execute(Job job);
	// 关闭线程池
	void shutdown();
	// 增加工作线程
	void addWorker(int num);
	// 减小工作线程
	void removeWorker(int num);
}
