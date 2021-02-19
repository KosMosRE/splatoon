package com.wechat.robot.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author QianMY
 * @date 2021-02-07 17:16
 */
public class NumPrint2 {
	private int n;
	private volatile int state = 0;

	public NumPrint2(int n) {
		this.n = n;
	}

	public void even() throws InterruptedException {
		for (int i = 2; i <= n; i += 2) {
			while (state != 2) {
				Thread.yield();
			}
			System.out.print(i);
			state = 0;
		}
	}

	public void odd() throws InterruptedException {
		for (int i = 1; i <= n; i += 2) {
			while (state != 1) {
				Thread.yield();
			}
			System.out.print(i);
			state = 0;
		}
	}

	public void zero() throws InterruptedException {
		for (int i = 1; i <= n; i++) {
			while (state != 0) {
				Thread.yield();
			}
			System.out.print("0");
			if (i % 2 == 0) {
				state = 2;
			} else {
				state = 1;
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		final NumPrint2 numPrint = new NumPrint2(9);
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
