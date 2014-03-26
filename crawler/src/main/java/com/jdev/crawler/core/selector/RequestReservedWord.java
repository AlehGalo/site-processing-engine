/**
 * 
 */
package com.jdev.crawler.core.selector;

/**
 * @author Aleh
 * 
 */
public enum RequestReservedWord {

    ACTION("${action}"), METHOD("${method}"), HOST("${host}"), PARAMS("${params}"), UUID("${uuid}"), DATE(
            "${date}"), DESCRIPTION("${description}");

    /**
     * Value of the word.
     */
    private String word;

    /**
     * @param value
     *            string.
     */
    private RequestReservedWord(final String value) {
        word = value;
    }

    /**
     * @return the word.
     */
    public final String getWord() {
        return word;
    }
}
