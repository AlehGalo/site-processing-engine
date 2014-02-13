/**
 * 
 */
package com.jdev.crawler.core.selector.jsoup;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
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
     * Suffix for selector.
     */
    private static final String SELECTOR_SUFFIX = ".sel";

    /**
     * Result suffix.
     */
    private static final String RESULT_SUFFIX = ".res";

    /**
     * @return Iterable resources.
     */
    @Parameters(name = "{index}: file name - {0}")
    public static Iterable<String> data() {
        return Arrays.asList(new String[] { "input1.html" });
    }

    /**
     * Resources input file, selectors file, result.
     */
    private final String selectorFileName;
    private final String resultFileName;
    private final String resourceFileName;

    /**
     * @param initFileName
     *            input file name.
     */
    public TestJSoupSelector(final String initFileName) {
        resultFileName = getFileContent(initFileName + RESULT_SUFFIX);
        resourceFileName = getFileContent(initFileName);
        selectorFileName = getFileContent(initFileName + SELECTOR_SUFFIX);
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

    /**
     * @param name
     *            file name in the path.
     * @return content of the file if found.
     */
    private String getFileContent(final String name) {
        try {
            return IOUtils.toString(this.getClass().getResourceAsStream(name));
        } catch (IOException e) {
            Assert.fail(e.getMessage());
            return null;
        }
    }
}
