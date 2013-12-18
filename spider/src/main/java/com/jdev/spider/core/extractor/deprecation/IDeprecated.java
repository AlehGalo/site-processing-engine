/**
 * 
 */
package com.jdev.spider.core.extractor.deprecation;


/**
 * @author Aleh
 * 
 */
public interface IDeprecated {

    /**
     * @param uri
     *            object.
     * @return true/false.
     */
    boolean isNotDeprecated(String uriPartOfUrl);
}
