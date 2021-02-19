package com.wechat.robot.concurrent;

import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author QianMY
 * @date 2021-02-08 09:35
 */
public class WorkMachine implements Runnable{
	private int worker;
	private Semaphore machine;

	public WorkMachine(int worker, Semaphore machine) {
		this.worker = worker;
		this.machine = machine;
	}

	/**
	 * If this thread was constructed using a separate
	 * <code>Runnable</code> run object, then that
	 * <code>Runnable</code> object's <code>run</code> method is called;
	 * otherwise, this method does nothing and returns.
	 * <p>
	 * Subclasses of <code>Thread</code> should override this method.
	 *
	 * @see #start()
	 * @see #stop()
	 */
	@SneakyThrows
	@Override
	public void run() {
		machine.acquire();
		System.out.println(String.format("第%d个工人正在使用机器", worker));
		Thread.sleep(2000);
		System.out.println(String.format("第%d个工人正在使用结束——————", worker));
		machine.release();
	}

	public static void main(String[] args) {
		//5台机器，8个工人
		final Semaphore machine = new Semaphore(5);
		ExecutorService service = Executors.newFixedThreadPool(8);
		for (int i = 1; i < 9; i++) {
			service.execute(new WorkMachine(i, machine));
		}

		service.shutdown();
	}
}
