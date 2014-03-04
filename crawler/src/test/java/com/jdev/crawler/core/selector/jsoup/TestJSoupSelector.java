/**
 * 
 */
package com.jdev.crawler.core.selector.jsoup;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.junit.runners.Parameterized.Parameters;

import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.TestAbstractResourcableSelector;
import com.jdev.crawler.core.selector.jsoup.extractor.IJSoupElementExtractor;

/**
 * @author Aleh
 * 
 */
public class TestJSoupSelector extends TestAbstractResourcableSelector<String> {

    /**
     * @return Iterable resources.
     */
    @Parameters(name = "{index}: file name - {0}, selector file name - {1}, result file name - {2}")
    public static Iterable<String[]> data() {
        return Arrays.<String[]> asList(new String[] { "input1.html", "input1.html.selector",
                "input1.html.result" }, new String[] { "input1.html", "input2.html.selector",
                "input2.html.result" }, new String[] { "input1.html", "input3.html.selector",
                "input3.html.result" });
    }

    /**
     * @param resourceFileName
     *            file name of resource.
     * @param selectorFileName
     *            selector for resource.
     * @param resultFileName
     *            result of selector.
     * @throws IOException
     *             exception.
     */
    public TestJSoupSelector(String resourceFileName, String selectorFileName, String resultFileName)
            throws IOException {
        super(resourceFileName, selectorFileName, resultFileName);
    }

    @Override
    public ISelector<String> createSelectorWithExtractor() {
        StringSourceJSoupSelector selector = new StringSourceJSoupSelector("seletor",
                getSelectorString());
        selector.setExtractor(new IJSoupElementExtractor() {

            @Override
            public String getValueFromRecord(final Element element) {
                if (element != null) {
                    return element.text();
                }
                return StringUtils.EMPTY;
            }
        });
        return selector;
    }
}
