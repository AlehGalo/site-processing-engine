package com.jdev.domain.adapter;

import java.util.Set;

/**
 * @author Aleh
 */
public interface IContent {

    /**
     * @return url of the resource.
     */
    String getUrl();

    /**
     * @return url content.
     */
    String getContent();

    /**
     * @return extracted text.
     */
    String getText();

    /**
     * Remove strings and free GC.
     */
    void resetStrings();

    /**
     * @return all links (within same domain) found on this web page
     */
    Set<IContent> getOutcomingLinks();
}
