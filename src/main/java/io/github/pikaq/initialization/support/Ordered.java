package io.github.pikaq.initialization.support;

public interface Ordered {

	int getOrder();

	Integer HIGHEST_LEVEL = Integer.MIN_VALUE;

	Integer LOWEST_LEVEL = Integer.MAX_VALUE;
}
