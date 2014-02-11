/**
 * 
 */
package com.jdev.crawler.core.selector.jsoup;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.jdev.crawler.core.selector.jsoup.extractor.IJSoupElementExtractor;
import com.jdev.crawler.exception.SelectionException;

/**
 * @author Aleh
 * 
 */
@RunWith(Parameterized.class)
public class TestJSoupSelector {

    /**
     * @return Iterable resources.
     */
    @Parameters(name = "{index}: file name - {0}")
    public static Iterable<String> data() {
        return Arrays.asList(new String[] { "input1.html" });
    }

    /**
     * 
     */
    private String resourceSelectorFileName, resourceResultFileName, resourceFileName;

    /**
     * @param initFileName
     *            file.
     */
    public TestJSoupSelector(final String initFileName) {

    }

    @Test
    public void correctSelector() throws SelectionException {
        StringSourceJSoupSelector selector = new StringSourceJSoupSelector("seletor", "variable");
        selector.setExtractor(new IJSoupElementExtractor() {

            @Override
            public String getValueFromRecord(Element element) {
                if (element != null) {
                    return element.text();
                }
                return StringUtils.EMPTY;
            }
        });
        Assert.assertTrue(selector.selectValues("").isEmpty());
    }
}
