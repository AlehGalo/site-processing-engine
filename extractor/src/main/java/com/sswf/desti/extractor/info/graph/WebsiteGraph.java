package com.sswf.desti.extractor.info.graph;

import java.util.*;

/**
 * @author Alexey Grigorev
 */
public class WebsiteGraph {

    private final Map<String, Set<WebLink>> outcoming = new HashMap<>();
    private final Map<String, Set<WebLink>> incoming = new HashMap<>();

    public void addEdge(String from, String to, String linkText) {
        addEdge(new WebLink(from, to, linkText));
    }

    public void addEdge(WebLink link) {
        put(outcoming, link.getFrom(), link);
        put(incoming, link.getTo(), link);
    }

    private static void put(Map<String, Set<WebLink>> map, String key, WebLink value) {
        Set<WebLink> set = map.get(key);
        if (set == null) {
            set = new HashSet<>();
            map.put(key, set);
        }
        set.add(value);
    }

    public Collection<WebLink> incomingLinks(String url) {
        if (!incoming.containsKey(url)) {
            return Collections.emptySet();
        }
        return incoming.get(url);
    }

    public Collection<WebLink> outcomingLinks(String url) {
        if (!outcoming.containsKey(url)) {
            return Collections.emptySet();
        }
        return outcoming.get(url);
    }

}
