package io.github.pikaq.common.util;

import java.util.concurrent.TimeUnit;

public class MixUtils {

	public static void runDelay(Runnable runnable, Long delay) {
		try {
			TimeUnit.SECONDS.sleep(delay);
			if (runnable != null) {
				runnable.run();
			}
		} catch (InterruptedException ignore) {
		}
	}

	public static void sleep(Integer delay) {
		try {
			TimeUnit.SECONDS.sleep(delay);
		} catch (InterruptedException ignore) {
		}
	}

}
