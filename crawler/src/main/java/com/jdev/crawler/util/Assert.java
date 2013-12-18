/**
 * 
 */
package com.jdev.crawler.util;

import java.util.Collection;

/**
 * @author Aleh
 * 
 */
public final class Assert {

    /**
     * Private constructor to hide it.
     */
    private Assert() {
    }

    /**
     * @param obj
     *            any object.
     * @param message
     *            string error message.
     */
    public static void notNull(final Object obj, final String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * @param obj
     *            any object.
     */
    public static void notNull(final Object obj) {
        notNull(obj, "Object cannot be null");
    }

    /**
     * @param str
     */
    public static void hasLength(final String str) {
        hasLength(str, "String cannot be null or empty");
    }

    /**
     * @param str
     *            any string.
     * @param message
     */
    public static void hasLength(final String str, final String message) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * @param clazz
     * @param obj
     */
    public static void isInstanceOf(final Class<?> clazz, final Object obj) {
        if (clazz == null || obj == null) {
            throw new IllegalArgumentException("Null input is not required");
        }
        if (!obj.getClass().isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Object is not of a required class");
        }
    }

    /**
     * @param value
     * @param message
     */
    public static void isTrue(final Boolean value, final String message) {
        if (Boolean.TRUE.equals(value)) {
            return;
        }
        throw new IllegalArgumentException(message);
    }

    /**
     * @param value
     */
    public static void isTrue(final Boolean value) {
        isTrue(value, "Not true");
    }

    /**
     * @param collection
     */
    public static void notEmpty(final Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException("Collection is null or empty");
        }
    }
}
