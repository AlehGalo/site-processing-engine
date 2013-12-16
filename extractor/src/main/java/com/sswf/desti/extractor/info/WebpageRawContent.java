package com.sswf.desti.extractor.info;

import com.sswf.desti.domain.adapter.IContent;

/**
 * @author Alexey Grigorev
 */
public class WebpageRawContent {

    private String url;
    private String rawHtml;

    public WebpageRawContent(String url, String rawHtml) {
        this.url = url;
        this.rawHtml = rawHtml;
    }

    public WebpageRawContent(IContent content) {
        this(content.getUrl(), content.getContent());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRawHtml() {
        return rawHtml;
    }

    public void setRawHtml(String rawHtml) {
        this.rawHtml = rawHtml;
    }

}
