/**
 * 
 */
package com.sswf.desti.spider.core.extractor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Aleh
 * 
 */
@RunWith(Parameterized.class)
public class TestDepthOfHref {

    private IDepth depth;

    private final String href;

    private final boolean expectation;

    public TestDepthOfHref(String href, boolean expected) {
        this.href = href;
        expectation = expected;
    }

    @Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] { { "http://abc.com", true }, { "https://abc.com", true },
                { "http://abc.com:8080/path/another/index.html", true },
                { "https://abc.com/a/b/c/d", false }, { "http://abc.com/s/f/d/f/file.html", false } };
        return Arrays.asList(data);
    }

    @Before
    public void init() {
        depth = new DepthOfHref(3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectDepth() {
        depth.isDepthAccepted(null);
    }

    @Test
    public void testUrl() {
        Assert.assertEquals(depth.isDepthAccepted(createUrl(href)), expectation);
    }

    private URL createUrl(final String href) {
        try {
            return new URL(href);
        } catch (MalformedURLException e) {
            Assert.fail(e.getMessage());
            return null;
        }
    }
}
