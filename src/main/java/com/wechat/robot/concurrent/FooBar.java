package com.wechat.robot.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author QianMY
 * @date 2021-02-07 17:16
 */
public class FooBar {
	private int n;

	private final Semaphore fooSemaphore = new Semaphore(1);
	private final Semaphore barSemaphore = new Semaphore(0);

	public FooBar(int n) {
		this.n = n;
	}

	public void foo(Runnable printFoo) throws InterruptedException {
		for (int i = 0; i < n; i++) {
			fooSemaphore.acquire();
			printFoo.run();
			barSemaphore.release();
		}
	}

	public void bar(Runnable printBar) throws InterruptedException {
		for (int i = 0; i < n; i++) {
			barSemaphore.acquire();
			printBar.run();
			fooSemaphore.release();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		final FooBar fooBar = new FooBar(5);
		ExecutorService service = Executors.newFixedThreadPool(2);
		for (int i = 0; i < 2; i++) {
			int finalI = i;
			service.execute(
					() -> {
						try {
							if (finalI == 0) {
								fooBar.foo(() -> System.out.print("foo"));
							} else {
								fooBar.bar(() -> System.out.println("bar/"));
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					});
		}

		service.shutdown();
	}
}
