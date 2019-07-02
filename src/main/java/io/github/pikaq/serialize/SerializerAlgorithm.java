package io.github.pikaq.serialize;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

/**
 * 序列化算法
 * @author pleuvoir
 *
 */
public enum SerializerAlgorithm {

	JSON(0),
	HESSIAN2(1),
	;

	@Getter
	private int code;

	SerializerAlgorithm(int code) {
		this.code = code;
	}

	/**
	 * 获取序列化算法，获取失败返回null
	 */
	public static SerializerAlgorithm toAlgorithm(int algorithmCode) {
		for (SerializerAlgorithm algorithm : SerializerAlgorithm.values())
			if (algorithm.getCode() == algorithmCode) {
				return algorithm;
			}
		return null;
	}

	/**
	 * 获取序列化算法，获取失败返回null
	 */
	public static SerializerAlgorithm toAlgorithm(String algorithmCode) {
		if(StringUtils.isBlank(algorithmCode)){
			return null;
		}
		try {
			Integer valueOf = Integer.valueOf(algorithmCode);
			return toAlgorithm(valueOf);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
}
