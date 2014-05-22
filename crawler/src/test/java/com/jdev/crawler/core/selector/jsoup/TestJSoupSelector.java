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
import com.jdev.crawler.core.selector.SelectUnit;
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
    @Parameters(name = "{index}: file name - {0}, selector file name - {1}, result file name - {2}, encoding - {3}")
    public static Iterable<String[]> data() {
        return Arrays.<String[]> asList(new String[] { "input1.html", "input1.html.selector",
                "input1.html.result", null }, new String[] { "input1.html", "input2.html.selector",
                "input2.html.result", null }, new String[] { "input1.html", "input3.html.selector",
                "input3.html.result", null }, new String[] { "input4.html", "input4.html.selector",
                "input4.html.result", "windows-1251" }, new String[] { "input4.html",
                "input5.html.selector", "input4.html.result", "windows-1251" }, new String[] {
                "input6.html", "input6.html.selector", "input6.html.result", "windows-1251" },
                new String[] { "input4.html", "input6.html.selector", "input4.html.result",
                        "windows-1251" }, new String[] { "input4.html", "input6_1.html.selector",
                        "input4.html.result", "windows-1251" }, new String[] { "input6.html",
                        "input6_1.html.selector", "input6.html.result", "windows-1251" });
    }

    /**
     * @param resourceFileName
     *            file name of resource.
     * @param selectorFileName
     *            selector for resource.
     * @param resultFileName
     *            result of selector.
     * @param charsetName
     *            charset name different from UTF-8.
     * @throws IOException
     *             exception.
     */
    public TestJSoupSelector(final String resourceFileName, final String selectorFileName,
            final String resultFileName, final String charsetName) throws IOException {
        super(resourceFileName, selectorFileName, resultFileName, charsetName);
    }

    @Override
    public ISelector<String> createSelector() {
        StringSourceJSoupSelector selector = new StringSourceJSoupSelector(new SelectUnit(
                "seletor", getSelectorString()));
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

    @Override
    public String convertStringToParameter(final String par) {
        return par;
    }
}
