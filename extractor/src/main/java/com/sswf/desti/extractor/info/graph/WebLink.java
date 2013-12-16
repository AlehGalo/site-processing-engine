package com.sswf.desti.extractor.info.graph;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * This class acts as an Edge in our {@link WebsiteGraph}
 * 
 * @author Alexey Grigorev
 */
public class WebLink {

    private final String from;
    private final String to;
    private final String text;

    public WebLink(String from, String to, String text) {
        this.from = from;
        this.to = to;
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return "WebLink [from=" + from + ", to=" + to + ", text=" + text + "]";
    }

}
