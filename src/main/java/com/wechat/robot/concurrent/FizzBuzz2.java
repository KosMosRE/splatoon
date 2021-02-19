package com.wechat.robot.concurrent;

import com.wechat.robot.thread.Examination;

import java.util.concurrent.*;

/**
 * @author QianMY
 * @date 2021-02-07 17:16
 */
public class FizzBuzz2 {
	public final static CyclicBarrier cb = new CyclicBarrier(4);
	private int n;

	public FizzBuzz2(int n) {
		this.n = n;
	}

	public void fizz() throws InterruptedException, BrokenBarrierException {

		for (int i = 1; i <= n; i++) {
			if (i % 3 == 0 && i % 5 != 0) {
				System.out.println("fizz");
			}
			cb.await();
		}

	}

	public void buzz() throws InterruptedException, BrokenBarrierException {
		for (int i = 1; i <= n; i++) {
			if (i % 3 != 0 && i % 5 == 0) {
				System.out.println("buzz");
			}
			cb.await();
		}
	}

	public void fizzbuzz() throws InterruptedException, BrokenBarrierException {
		for (int i = 1; i <= n; i++) {
			if (i % 3 == 0 && i % 5 == 0) {
				System.out.println("fizzbuzz");
			}
			cb.await();
		}
	}

	public void number() throws InterruptedException, BrokenBarrierException {
		for (int i = 1; i <= n; i++) {
			if (i % 3 != 0 && i % 5 != 0) {
				System.out.println(i);
			}
			cb.await();
		}
	}

	public static void main(String[] args) throws InterruptedException {

		Examination.start();
		final FizzBuzz2 fizzBuzz = new FizzBuzz2(500);

		final ExecutorService executorService = Executors.newFixedThreadPool(4);

		executorService.execute(() -> {
			try {
				fizzBuzz.fizz();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		});

		executorService.execute(() -> {
			try {
				fizzBuzz.fizzbuzz();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		});

		executorService.execute(() -> {
			try {
				fizzBuzz.number();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		});

		executorService.execute(() -> {
			try {
				fizzBuzz.buzz();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		});
		Examination.end();
		executorService.shutdown();

	}
}
