package com.wechat.robot.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author QianMY
 * @date 2021-02-07 17:16
 */
public class NumPrint {
	private int n;

	private final Semaphore evenSemaphore = new Semaphore(0);
	private final Semaphore oddSemaphore = new Semaphore(0);
	private final Semaphore zeroSemaphore = new Semaphore(1);

	public NumPrint(int n) {
		this.n = n;
	}

	public void even() throws InterruptedException {
		for (int i = 1; i <= n; i = i + 2) {
			evenSemaphore.acquire();
			System.out.print(i);
			zeroSemaphore.release();
		}
	}

	public void odd() throws InterruptedException {
		for (int i = 2; i <= n; i = i + 2) {
			oddSemaphore.acquire();
			System.out.print(i);
			zeroSemaphore.release();
		}
	}

	public void zero() throws InterruptedException {
		for (int i = 0; i < n; i++) {
			zeroSemaphore.acquire();
			System.out.print("0");
			if (i % 2 == 0) {
				evenSemaphore.release();
			} else {
				oddSemaphore.release();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		final NumPrint numPrint = new NumPrint(9);
		ExecutorService service = Executors.newFixedThreadPool(3);
		for (int i = 1; i < 4; i++) {
			int finalI = i;
			service.execute(
					() -> {
						try {
							switch (finalI) {
								case 1:
									numPrint.even();
									break;
								case 2:
									numPrint.odd();
									break;
								case 3:
									numPrint.zero();
									break;
								default:
									break;
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					});
		}

		service.shutdown();
	}
}
