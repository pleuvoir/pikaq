package io.github.pikaq.common.util;

import com.alibaba.fastjson.JSON;

public interface ToJSON {

	default String toJSON() {
		return JSON.toJSONString(this);
	}
}
