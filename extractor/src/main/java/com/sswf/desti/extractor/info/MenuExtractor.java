package com.sswf.desti.extractor.info;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.sswf.desti.extractor.info.cleaner.Cleaner;
import com.sswf.desti.extractor.info.graph.WebLink;
import com.sswf.desti.extractor.info.graph.WebsiteGraph;

/**
 * @author Alexey Grigorev
 */
public class MenuExtractor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuExtractor.class);

    private final WebsiteContent website;
    private final WebpageRawContent webpage;
    private final List<MenuItem> potentialCandidates = Lists.newArrayList();

    public MenuExtractor(WebsiteContent website, WebpageRawContent webpage) {
        this.website = website;
        this.webpage = webpage;
    }

    public boolean isMenu() {
        WebsiteGraph graph = website.getGraph();
        Collection<WebLink> incomingLinks = graph.incomingLinks(webpage.getUrl());

        Set<String> linksText = new HashSet<>();
        for (WebLink link : incomingLinks) {
            linksText.add(link.getText());
        }

        double score = 0.0;

        for (String text : linksText) {
            if (text.toLowerCase().contains("menu")) {
                score = score + 1;
                break;
            }
        }

        if (webpage.getUrl().contains("menu")) {
            score = score + 1;
        }

        String rawHtml = webpage.getRawHtml();
        Document dirtyDoc = Jsoup.parse(rawHtml);
        String title = dirtyDoc.select("title").text();

        if (title.toLowerCase().contains("menu")) {
            score = score + 1;
        }

        return score >= 1.0;
    }

    public void process() {
        String rawHtml = webpage.getRawHtml();
        // may be separators? 
        rawHtml = rawHtml.replaceAll("\\*", "").replaceAll("_", "").replaceAll("\\s+", " ");

        Document dirtyDoc = Jsoup.parse(rawHtml);
        if (dirtyDoc.body() == null) {
            LOGGER.warn("{} has no body", webpage.getUrl());
            return;
        }

        Document cleanDoc = Cleaner.clean(dirtyDoc);
        Element body = cleanDoc.body();
        if (body == null) {
            LOGGER.warn("something wierd happened!!!!11");
            return;
        }

        LOGGER.debug("processing the following html: {}", body.html());

        dfs(body, 0);
    }

    private static final Pattern PRICE_PATTERN = Pattern.compile("\\$\\s*\\d+.?\\d*");

    private static String extractPrice(String cand) {
        Matcher matcher = PRICE_PATTERN.matcher(cand);
        if (matcher.find()) {
            String price = matcher.group();
            return price.replaceAll("\\s+", "");
        }
        return "";
    }

    private void dfs(Element element, int depth) {
        String text = element.text();
        if (text.length() > 150) {
            // too long, can't be a menu item
            for (Element child : element.children()) {
                if (child.hasText()) {
                    dfs(child, depth + 1);
                }
            }
            return;
        }

        if (text.length() < 8) {
            // too short!
            return;
        }

        for (Element child : element.children()) {
            dfs(child, depth + 1);
        }

        String[] words = text.split("\\s+");
        if (words.length <= 3) {
            // too few words
            return;
        }

        int pricesOcc = StringUtils.countMatches(text, "$");

        if (pricesOcc != 1) {
            // no prices or too many
            return;
        }

        LOGGER.info("good candidate for menu: '{}', len={}", text, text.length());
        String price = extractPrice(text);
        potentialCandidates.add(new MenuItem("", text.trim(), price));
    }

    public List<MenuItem> extract() {
        if (!isMenu()) {
            return Collections.emptyList();
        }

        process();
        return potentialCandidates;
    }

    public List<MenuItem> getPotentialCandidates() {
        return potentialCandidates;
    }
}
