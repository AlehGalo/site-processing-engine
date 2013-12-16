package com.sswf.desti.domain;

/**
 * @author Alexey Grigorev
 */
public class TargetWebSite {

    private final String id;
    private final String poi;
    private final String url;

    public TargetWebSite(String id, String poi, String url) {
        this.id = id;
        this.poi = poi;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getPoi() {
        return poi;
    }

    public String getUrl() {
        return url;
    }

}
