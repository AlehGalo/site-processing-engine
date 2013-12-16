package com.sswf.desti.spider.core.extractor;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.sswf.desti.domain.adapter.IContent;
import com.sswf.desti.spider.core.extractor.deprecation.DeprecationFactory;
import com.sswf.desti.spider.core.extractor.deprecation.IDeprecated;

/**
 * @author Alexey Grigorev
 */
public class LinksExtractorTest {

    @Test
    public void test() throws IOException {
        String url = "http://www.website.net/";
        LinksExtractor extractor = new LinksExtractor(url);
        IDeprecated deprecated = DeprecationFactory.createDeprecationRules();
        extractor.setDeprecated(deprecated);

        String page = readPage("webpage1.html");

        Set<IContent> result = extractor.extract(page);
        @SuppressWarnings("deprecation")
        Set<IContent> expected = extractor.extractOld(page);

        assertEquals(expected, result);
        System.out.println(expected);
    }

    private static String readPage(String file) throws IOException {
        InputStream page = LinksExtractorTest.class.getResourceAsStream(file);
        return IOUtils.toString(page);
    }

}
