package com.wechat.robot.thread;

import cn.hutool.aop.aspects.SimpleAspect;

import java.lang.reflect.Method;

/**
 * @author QianMY
 * @date 2021-02-01 16:52
 */
public class Thread {

	public static class MyThread extends java.lang.Thread {
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
		@Override
		public void run() {
			System.out.println(String.format("hello, myThread: %s", java.lang.Thread.currentThread().getName()));
		}
	}

	public static class Student {
		String name = "qian";

		public void say() {
			System.out.println("说话1");
		}

		public void doing() {
			System.out.println("正在做---");
		}
	}

	public static class Handlers extends SimpleAspect {
		@Override
		public boolean before(Object target, Method method, Object[] args) {
			if (method.getName().equals("say")) {
				System.out.println("切入" + method.getName());
				return true;
			}
			return true;
		}
	}

	public static void main(String[] args) {
		int i = 0;
		final int[] ints = {0, 1, 2, 3};
		final int[] ints1 = {0, 1, 3};
		for (int anInt : ints) {
			i = i ^ anInt;
		}
		for (int i1 : ints1) {
			i = i1 ^ i;
		}

		System.out.println(i);

	}
}

