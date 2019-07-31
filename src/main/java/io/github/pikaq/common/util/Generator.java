package io.github.pikaq.common.util;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

/**
 * 生成uuid并去掉 "-"
 *
 */
public class Generator {
	
	/**
	 * 横线
	 */
	private static final String HORIZONTAL = "-";

	private static TimeBasedGenerator timeBasedGenerator;

	static {
		timeBasedGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
	}

	/**
	 * 生成下一个UUID
	 */
	public static String nextUUID() {
		return timeBasedGenerator.generate().toString().replace(HORIZONTAL, StringUtils.EMPTY);
	}

}