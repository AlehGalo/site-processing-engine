/**
 * 
 */
package com.jdev.crawler.core.selector.xpath;

import java.io.IOException;
import java.util.Arrays;

import org.junit.runners.Parameterized.Parameters;

import com.jdev.crawler.core.selector.ISelector;
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
                "input1.htm.result" }, new String[] { "input1.htm", "input2.htm.selector",
                "input2.htm.result" }, new String[] { "input1.htm", "input3.htm.selector",
                "input3.htm.result" });
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
    public TestXPathSelector(final String resourceFileName, final String selectorFileName,
            final String resultFileName) throws IOException {
        super(resourceFileName, selectorFileName, resultFileName);
    }

    @Override
    public ISelector<String> createSelectorWithExtractor() {
        return new XPathSelector("seletor", getSelectorString());
    }

    @Override
    public String convertStringToParameter(final String par) {
        return par;
    }
}
