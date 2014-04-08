/**
 * 
 */
package com.jdev.domain.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * @author Aleh
 * 
 */
public final class ReflectionUtils {

    /**
     * Consts.
     */
    private static final String GET = "get", SET = "set", DEPRECATED_METHOD = "getId";

    /**
     * Hide public constructor.
     */
    private ReflectionUtils() {
    }

    /**
     * @param source
     * @param dest
     */
    public static <T> void copyValuesExceptGetId(T source, T dest) {
        Assert.notNull(source);
        Assert.notNull(dest);
        Method methods[] = dest.getClass().getMethods();
        Class<?> clazz = source.getClass();
        for (Method method : methods) {
            String methodName = method.getName();
            if (!StringUtils.equals(DEPRECATED_METHOD, methodName) && methodName.startsWith(GET)
                    && method.getDeclaringClass().equals(clazz)) {
                String setterName = SET + StringUtils.removeStart(methodName, GET);
                try {
                    clazz.getMethod(setterName, method.getReturnType()).invoke(dest,
                            method.invoke(source, new Object[] {}));
                } catch (InvocationTargetException | IllegalArgumentException
                        | IllegalAccessException | NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static <T> boolean compareObjects(T source, T dest) {
        Assert.notNull(source);
        Assert.notNull(dest);
        Method methods[] = dest.getClass().getMethods();
        Class<?> clazz = source.getClass();
        for (Method method : methods) {
            String methodName = method.getName();
            if (!StringUtils.equals(DEPRECATED_METHOD, methodName) && methodName.startsWith(GET)
                    && method.getDeclaringClass().equals(clazz)) {
                try {
                    Object sourceReturned = method.invoke(source, new Object[] {});
                    Object destReturned = method.invoke(dest, new Object[] {});
                    if (sourceReturned != null && destReturned != null) {
                        if (!sourceReturned.equals(destReturned)) {
                            return false;
                        }
                    } else if (sourceReturned != destReturned) {
                        return false;
                    }
                } catch (IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
