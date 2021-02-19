package com.wechat.robot.concurrent;

import com.wechat.robot.thread.Examination;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author QianMY
 * @date 2021-02-07 17:16
 */
public class FizzBuzz {
	private int n;

	Semaphore f = new Semaphore(0);
	Semaphore b = new Semaphore(0);
	Semaphore fb = new Semaphore(0);
	Semaphore num = new Semaphore(1);


	public FizzBuzz(int n) {
		this.n = n;
	}

	public void fizz() throws InterruptedException {

		for (int i = 1; i <= n; i++) {
			if (i % 3 == 0 && i % 5 != 0) {
				f.acquire();
				System.out.println("fizz");
				num.release();
			}
		}

	}

	public void buzz() throws InterruptedException {
		for (int i = 1; i <= n; i++) {
			if (i % 3 != 0 && i % 5 == 0) {
			b.acquire();
			System.out.println("buzz");
			num.release();}
		}
	}

	public void fizzbuzz() throws InterruptedException {
		for (int i = 1; i <= n; i++) {
			if (i % 3 == 0 && i % 5 == 0) {
			fb.acquire();
			System.out.println("fizzbuzz");
			num.release();
			}
		}
	}

	public void number() throws InterruptedException {
		for (int i = 1; i <= n; i++) {
			num.acquire();
			if (i % 3 == 0 && i % 5 == 0) {
				fb.release();
			} else if (i % 3 == 0) {
				f.release();
			} else if (i % 5 == 0) {
				b.release();
			} else {
				System.out.println(i);
				num.release();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {

		Examination.start();
		final FizzBuzz fizzBuzz = new FizzBuzz(500);

		final ExecutorService executorService = Executors.newFixedThreadPool(4);

		executorService.execute(() -> {
			try {
				fizzBuzz.fizz();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		executorService.execute(() -> {
			try {
				fizzBuzz.fizzbuzz();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		executorService.execute(() -> {
			try {
				fizzBuzz.number();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		executorService.execute(() -> {
			try {
				fizzBuzz.buzz();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		Examination.end();
		executorService.shutdown();

	}
}
