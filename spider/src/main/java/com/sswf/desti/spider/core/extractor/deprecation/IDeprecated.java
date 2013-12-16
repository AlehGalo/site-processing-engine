/**
 * 
 */
package com.sswf.desti.spider.core.extractor.deprecation;


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
