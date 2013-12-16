package com.sswf.desti.domain.adapter;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Aleh
 * 
 */
public class ContentUrl implements IContent {

    private final String url;

    private String content;
    private String text;

    private Set<IContent> outcomingLinks;

    /**
     * @param url
     * @param content
     * @param text
     */
    public ContentUrl(String url, String content, String text) {
        this.url = url;
        this.content = content;
        this.text = text;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Set<IContent> getOutcomingLinks() {
        if (outcomingLinks == null) {
            return Collections.emptySet();
        }
        return outcomingLinks;
    }

    public void setOutcomingLinks(Set<IContent> outcomingLinks) {
        this.outcomingLinks = outcomingLinks;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        ContentUrl contentUrl = (ContentUrl) obj;
        return ObjectUtils.equals(url, contentUrl.url);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(url);
    }

    @Override
    public String toString() {
        return "ContentUrl [url=" + url + ", content=" + StringUtils.abbreviate(content, 50) + ", text="
                + StringUtils.abbreviate(text, 50) + "]";
    }

    @Override
    public void resetStrings() {
        text = StringUtils.EMPTY;
        content = StringUtils.EMPTY;
        outcomingLinks = Collections.emptySet();
    }

}
