/**
 * 
 */
package com.sswf.desti.extractor.common;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sswf.desti.domain.adapter.IContent;
import com.sswf.desti.extractor.IExtractor;

/**
 * @author Aleh
 * 
 */
public class PriceExtractor implements IExtractor<Set<String>, IContent> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PriceExtractor.class);

    /**
     * 
     */
    private static final List<String> PARENTS = Arrays.asList("h3", "div");

    /**
     * Pattern for price.
     */
    public static final String PRICE_PATTERN = "[{0}]\t{1}";

    /**
     * |(\\$\\s*(\\d+(\\.(\\d{2}))?))
     */
    private final Pattern pattern = Pattern
            .compile("(\\$\\s*(\\d+(\\.(\\d{2}))?(\\s*[\\-/]\\s*\\$\\s*\\d+(\\.(\\d{2}))?)?))");

    /**
     * 
     */
    private static final int LOOP_LIMIT = 150;

    /**
     * 
     */
    private final Set<String> set;

    /**
     * 
     */
    public PriceExtractor() {
        set = new HashSet<>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sswf.desti.extractor.IExtractor#extract(java.lang.Object)
     */
    @Override
    public Set<String> extract(final IContent e) {
        set.clear();
        StringBuilder content = new StringBuilder(e.getContent());
        String res = content.toString();
        int i = 0;
        while (res.length() != 0) {
            res = findPriceAndDeleteItFromHtml(content);
            content = new StringBuilder(res);
            if (LOOP_LIMIT <= i++) {
                LOGGER.error("Page enetered infinity loop: " + e.getUrl());
                break;
            }
        }
        return set;
    }

    /**
     * @param content
     * @return
     */
    private String findPriceAndDeleteItFromHtml(final StringBuilder content) {
        final Document document = Jsoup.parse(content.toString());
        final Matcher m = pattern.matcher(document.text());
        if (m.find()) {
            final String price = m.group();
            final Element element = document.select(
                    MessageFormat.format(":containsOwn({0})", price)).first();
            if (element != null) {
                final Element elementToProcess = getParentOrThisElement(element, price);
                String source = elementToProcess.text();
                if (StringUtils.isNotBlank(source)) {
                    set.add(MessageFormat.format(PRICE_PATTERN, price,
                            getRemovedPriceIfStartsOrEndsWith(source, price)));
                }
                elementToProcess.remove();
            }
            return document.html();
        }
        return StringUtils.EMPTY;
    }

    /**
     * Limit for parent search.
     */
    private static final int PARENT_LIMIT = 5;

    /**
     * @param element
     * @param price
     * @return parent element relevant to search criteria.
     */
    private Element getParentOrThisElement(final Element element, final String price) {
        if (element != null
                && StringUtils.isBlank(getRemovedPriceIfStartsOrEndsWith(element.text(), price))) {
            Element processEl = element;
            int counter = 0;
            while (processEl != null && !PARENTS.contains(processEl.tagName())
                    && counter < PARENT_LIMIT) {
                processEl = processEl.parent();
                ++counter;
            }
            if (counter < PARENT_LIMIT) {
                return processEl;
            }
        }
        return element;
    }

    /**
     * @param source
     *            text.
     * @param price
     *            of the event.
     * @return price text extracted from source.
     */
    private String getRemovedPriceIfStartsOrEndsWith(final String source, String price) {
        if (StringUtils.startsWith(source, price)) {
            return StringUtils.removeStart(source, price).trim();
        }
        if (StringUtils.endsWith(source, price)) {
            return StringUtils.removeEnd(source, price).trim();
        }
        return source.trim();
    }
}
