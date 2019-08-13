package io.github.pikaq;

import io.netty.util.AttributeKey;

/**
 * 方便绑定Channel自定义属性，保证单例
 */
public class AttributeKeyConst {

    /**
     * 客户端编号
     */
    public static final AttributeKey<Integer> CLIENT_ID = AttributeKey.newInstance("clientID");
}
