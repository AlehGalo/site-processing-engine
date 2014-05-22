/**
 * 
 */
package com.jdev.crawler.core.selector.xpath;

import java.io.IOException;
import java.util.Arrays;

import org.junit.runners.Parameterized.Parameters;

import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.SelectUnit;
import com.jdev.crawler.core.selector.TestAbstractResourcableSelector;

/**
 * @author Aleh
 * 
 */
public class TestXPathSelector extends TestAbstractResourcableSelector<String> {

    /**
     * @return Iterable resources.
     */
    @Parameters(name = "{index}: file name - {0}, selector file name - {1}, result file name - {2}")
    public static Iterable<String[]> data() {
        return Arrays.<String[]> asList(new String[] { "input1.htm", "input1.htm.selector",
                "input1.htm.result", null }, new String[] { "input1.htm", "input2.htm.selector",
                "input2.htm.result", null }, new String[] { "input1.htm", "input3.htm.selector",
                "input3.htm.result", null }, new String[] { "input-fl-ru-utf8.htm",
                "input-fl-ru.selector", "input-fl-ru.result", null }, new String[] {
                "input-fl-ru-utf8.htm", "input-fl-ru1.selector", "input-fl-ru1.result", null },
                new String[] { "input4.html", "input4.html.selector", "input4.html.result",
                        "windows-1251" });
    }

    /**
     * @param resourceFileName
     *            file name of resource.
     * @param selectorFileName
     *            selector for resource.
     * @param resultFileName
     *            result of selector.
     * @param charsetName
     * @throws IOException
     *             exception.
     */
    public TestXPathSelector(final String resourceFileName, final String selectorFileName,
            final String resultFileName, final String charsetName) throws IOException {
        super(resourceFileName, selectorFileName, resultFileName, charsetName);
    }

    @Override
    public ISelector<String> createSelector() {
        return new XPathSelector(new SelectUnit("seletor", getSelectorString()));
    }

    @Override
    public String convertStringToParameter(final String par) {
        return par;
    }
}
