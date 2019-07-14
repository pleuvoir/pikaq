package io.github.pikaq.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 支持 properties 文件到对象的转换
 */
public interface Propertiesable extends ToJSON{

    default void load(String propPath) {
        PropLoaderUtil.setTargetFromProperties(this, propPath);
    }

    default void load(String propPath, String ignorePrefix) {
        PropLoaderUtil.setTargetFromProperties(this, propPath, ignorePrefix);
    }

    default void copyStateTo(Object target) {
        for (Field field : getClass().getDeclaredFields()) {
            if (!Modifier.isFinal(field.getModifiers())) {
                field.setAccessible(true);
                try {
                    field.set(target, field.get(this));
                } catch (Exception e) {
                    throw new RuntimeException("Failed to copy state: " + e.getMessage(), e);
                }
            }
        }
    }
}