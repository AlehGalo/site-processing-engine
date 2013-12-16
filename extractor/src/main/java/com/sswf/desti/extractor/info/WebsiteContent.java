package com.sswf.desti.extractor.info;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.sswf.desti.extractor.info.graph.WebLink;
import com.sswf.desti.extractor.info.graph.WebsiteGraph;

/**
 * @author Alexey Grigorev
 */
public class WebsiteContent {

    private String url;
    private List<WebpageRawContent> pages = Lists.newArrayList();
    private WebsiteGraph graph = new WebsiteGraph();

    public WebsiteContent(String url) {
        this.url = url;
    }

    public void addPage(WebpageRawContent page, Set<WebLink> links) {
        pages.add(page);
        for (WebLink link : links) {
            graph.addEdge(link);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<WebpageRawContent> getPages() {
        return pages;
    }

    public void setPages(List<WebpageRawContent> pages) {
        this.pages = pages;
    }

    public WebsiteGraph getGraph() {
        return graph;
    }

    public void setGraph(WebsiteGraph graph) {
        this.graph = graph;
    }

}
