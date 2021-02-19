package com.wechat.robot.concurrent;

import java.util.concurrent.*;

/**
 * @author QianMY
 * @date 2021-02-07 17:16
 */
public class water {

	private int n;
	private int oxCount;
	private int hxCount;

	Semaphore hx = new Semaphore(0);
	Semaphore ox = new Semaphore(1);


	public water(int n) {
		this.n = n;
	}

	public void hydrogen() throws InterruptedException, BrokenBarrierException {
		for (int i = 0; i < n; i++) {
			hx.acquire();
			System.out.print("H");
			hxCount++;
			if (hxCount >= 2) {
				hxCount = 0;
				ox.release();
			}
		}
	}

	public void oxygen() throws InterruptedException, BrokenBarrierException {
		for (int i = 0; i < n; i++) {
			ox.acquire();
			System.out.print("O");
			oxCount++;
			if (oxCount >= 1) {
				oxCount = 0;
				hx.release(2);
			}
		}
	}


	public static void main(String[] args) throws InterruptedException {
		final water water = new water(1);

		final ExecutorService executorService = Executors.newFixedThreadPool(3);

		executorService.execute(() -> {
			try {
				water.oxygen();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		});

		executorService.execute(() -> {
			try {
				water.hydrogen();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		});

		executorService.execute(() -> {
			try {
				water.hydrogen();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		});

		executorService.shutdown();
	}
}
