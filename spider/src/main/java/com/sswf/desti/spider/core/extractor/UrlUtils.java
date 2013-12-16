package com.sswf.desti.spider.core.extractor;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexey Grigorev
 */
public class UrlUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlUtils.class);

    public static URL buildUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            LOGGER.warn("got malformed url: {}", url);
            return null;
        }
    }

    public static String extractDomain(URL url) {
        return url.getProtocol() + "://" + url.getHost();
    }

    public static String truncateFromLastSlash(URL url) {
        String domain = extractDomain(url);
        String path = url.getPath();
        if (StringUtils.isNotEmpty(path)) {
            return domain + path.substring(0, path.lastIndexOf('/'));
        } else {
            return domain;
        }
    }
}
