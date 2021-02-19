package com.wechat.robot.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author QianMY
 * @date 2021-02-08 09:35
 */
public class SeqPrint {

	CountDownLatch second = new CountDownLatch(1);
	CountDownLatch third = new CountDownLatch(1);

	public void first() throws InterruptedException {
		System.out.println("first");
		second.countDown();
	}

	public void sec() throws InterruptedException {
		second.await();
		System.out.println("second");
		third.countDown();
	}

	public void third() throws InterruptedException {
		third.await();
		System.out.println("third");
	}

	public void run(int i) throws InterruptedException {
		switch (i) {
			case 1:
				first();
				break;
			case 2:
				sec();
				break;
			case 3:
				third();
				break;
			default:
		}
	}

	public static void main(String[] args) {
		final SeqPrint seqPrint = new SeqPrint();
		ExecutorService service = Executors.newCachedThreadPool();
		for (int i = 1; i < 4; i++) {
			int finalI = i;
			service.execute(
					() -> {
						try {
							seqPrint.run(finalI);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					});
		}
		service.shutdown();
	}
}
