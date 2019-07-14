package io.github.pikaq.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class PropLoaderUtil {

	 /**
     * 加载 properties 文件，可以使用绝对路径或者相对路径
     * @param propertyFileName	文件名称，可以使用绝对路径或者相对路径
     * @return	返回设值后的 Properties 对象
     */
    public static Properties loadProperties(String propertyFileName) {
        Properties props = new Properties();
        final File propFile = new File(propertyFileName);
        try (final InputStream is = propFile.isFile() ? new FileInputStream(propFile)
                : PropLoaderUtil.class.getClassLoader().getResourceAsStream(propertyFileName)) {
            if (is != null) {
                props.load(is);
            } else {
                throw new IllegalArgumentException("Cannot find property file: " + propertyFileName);
            }
        } catch (IOException io) {
            throw new RuntimeException("Failed to read property file", io);
        }
        return props;
    }

    /**
     * 复制 Properties 内容并返回一个新的 Properties 对象
     * @param props	原 Properties 对象
     * @return	赋值后新的 Properties 对象
     */
    public static Properties copyProperties(final Properties props) {
        Properties copy = new Properties();
        props.forEach((key, value) -> copy.setProperty(key.toString(), value.toString()));
        return copy;
    }

    // ####### 完成文件到对象的转换

    /**
     * 完成文件到对象的转换 <br>
     * 注意：尝试使用类中变量名进行转换，如果类型为布尔且以 is 开头，请配置 properties 文件时去除 is，否则会报错
     * @param target	待转换的对象
     * @param propertyFileName 配置文件名称
     * @param ignorePrefix 忽略前缀
     * @return	转换后的对象
     */
    public static <T> T setTargetFromProperties(final T target, final String propertyFileName, String ignorePrefix) {
        if (target == null || propertyFileName == null) {
            throw new IllegalArgumentException("target or propertyFileName must be non-null.");
        }
        List<Method> methods = Arrays.asList(target.getClass().getMethods());
        Properties props = loadProperties(propertyFileName);
        Iterator<Map.Entry<Object, Object>> iterator = props.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) iterator.next();
            String key = entry.getKey().toString();
            Object value = entry.getValue();
            if (key.startsWith(ignorePrefix)) { // 对前缀进行忽略处理
                key = key.substring(ignorePrefix.length());
            }
            setProperty(target, key, value, methods);
        }
        return target;
    }

    /**
     * 完成文件到对象的转换 <br>
     * 注意：尝试使用类中变量名进行转换，如果类型为布尔且以 is 开头，请配置 properties 文件时去除 is，否则会报错
     * @param target	待转换的对象
     * @param propertyFileName 配置文件名称
     * @return	转换后的对象
     */
    public static <T> T setTargetFromProperties(final T target, final String propertyFileName) {
        if (target == null || propertyFileName == null) {
            throw new IllegalArgumentException("target or propertyFileName must be non-null.");
        }
        List<Method> methods = Arrays.asList(target.getClass().getMethods());
        loadProperties(propertyFileName).forEach((key, value) -> {
            setProperty(target, key.toString(), value, methods);
        });
        return target;
    }

    /**
     * 完成文件到对象的转换
     * @param target	待转换的对象
     * @param properties 配置文件
     * @return	转换后的对象
     */
    public static <T> T setTargetFromProperties(final T target, final Properties properties) {
        if (target == null || properties == null) {
            throw new IllegalArgumentException("target or properties must be non-null.");
        }
        List<Method> methods = Arrays.asList(target.getClass().getMethods());
        properties.forEach((key, value) -> {
            setProperty(target, key.toString(), value, methods);
        });
        return target;
    }

    // 尝试使用类中变量名进行转换，如果类型为布尔且以 is 开头，请配置 properties 文件时去除 is，否则会报错
    private static void setProperty(final Object target, final String propName, final Object propValue,
                                    final List<Method> methods) {

        String methodName = "set" + propName.substring(0, 1).toUpperCase(Locale.ENGLISH) + propName.substring(1);
        Method writeMethod = methods.stream().filter(m -> m.getName().equals(methodName) && m.getParameterCount() == 1).findFirst().orElse(null);

        if (writeMethod == null) {
            String booleanMethodName =  "is" + propName.substring(0, 1).toUpperCase(Locale.ENGLISH) + propName.substring(1);
            writeMethod = methods.stream().filter(m -> m.getName().equals(booleanMethodName) && m.getParameterCount() == 1).findFirst().orElse(null);
        }

        if (writeMethod == null) {
            throw new RuntimeException(String.format("Property %s does not exist on target %s", propName, target.getClass()));
        }

        try {
            // 根据参数类型尝试
            Class<?> paramClass = writeMethod.getParameterTypes()[0];
            if (paramClass == int.class) {
                writeMethod.invoke(target, Integer.parseInt(propValue.toString()));
            }
            else if (paramClass == long.class) {
                writeMethod.invoke(target, Long.parseLong(propValue.toString()));
            }
            else if (paramClass == boolean.class || paramClass == Boolean.class) {
                writeMethod.invoke(target, Boolean.parseBoolean(propValue.toString()));
            }
            else if (paramClass == String.class) {
                writeMethod.invoke(target, propValue.toString());
            }
            else if (paramClass == double.class) {
                writeMethod.invoke(target, Double.valueOf(propValue.toString()));
            }
            else {
                try {
                    writeMethod.invoke(target, Class.forName(propValue.toString()).newInstance());
                }
                catch (InstantiationException | ClassNotFoundException e) {
                    writeMethod.invoke(target, propValue);
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
