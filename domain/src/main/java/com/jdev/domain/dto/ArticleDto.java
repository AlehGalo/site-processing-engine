/**
 * 
 */
package com.jdev.domain.dto;

import java.io.Serializable;

/**
 * @author Aleh
 * 
 */
public class ArticleDto implements Serializable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private String url, title, resource;

    /**
     * 
     */
    public ArticleDto() {
    }

    /**
     * @param url
     * @param title
     * @param resource
     */
    public ArticleDto(final String url, final String title, final String resource) {
        this.url = url;
        this.title = title;
        this.resource = resource;
    }

    /**
     * @return the url
     */
    public final String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public final void setUrl(final String url) {
        this.url = url;
    }

    /**
     * @return the title
     */
    public final String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public final void setTitle(final String title) {
        this.title = title;
    }

    /**
     * @return the resource
     */
    public final String getResource() {
        return resource;
    }

    /**
     * @param resource
     *            the resource to set
     */
    public final void setResource(final String resource) {
        this.resource = resource;
    }

}
