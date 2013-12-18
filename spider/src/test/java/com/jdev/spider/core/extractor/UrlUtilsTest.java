package com.jdev.spider.core.extractor;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.junit.Test;

/**
 */
public class UrlUtilsTest {

    @Test
    public void truncateFromLastSlash() {
        String stringUrl = "http://www.marketstreetgrillsf.com/main/msg-menu.asp?p=2&m=breakfast";
        URL url = UrlUtils.buildUrl(stringUrl);
        String name = UrlUtils.truncateFromLastSlash(url);
        assertEquals("http://www.marketstreetgrillsf.com/main", name);
    }

    @Test
    public void truncateFromLastSlash_alreadyTop() {
        String stringUrl = "http://www.marketstreetgrillsf.com";
        URL url = UrlUtils.buildUrl(stringUrl);
        String name = UrlUtils.truncateFromLastSlash(url);
        assertEquals("http://www.marketstreetgrillsf.com", name);
    }

}
