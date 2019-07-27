package io.github.pikaq.common.util;

import java.util.concurrent.CountDownLatch;

/**
 * 方便等待
 */
public class Waiter {

	public static Waiter create() {
		return new Waiter();
	}

	private final CountDownLatch await = new CountDownLatch(1);

	public void on() {
		try {
			await.await();
		} catch (InterruptedException ignored) {
		}
	}

	public void off() {
		await.countDown();
	}

	public void waitUntilDead() {
		try {
			Thread.currentThread().join();
		} catch (InterruptedException ignored) {
		}
	}
	
}
