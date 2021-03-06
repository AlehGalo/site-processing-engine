package com.jdev.crawler.core.selector.regexp;

import java.io.IOException;
import java.util.Arrays;

import org.junit.runners.Parameterized.Parameters;

import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.TestAbstractResourcableSelector;

public class TestRegexpSelector extends TestAbstractResourcableSelector<String> {

    /**
     * @return Iterable resources.
     */
    @Parameters(name = "{index}: file name - {0}, selector file name - {1}, result file name - {2}")
    public static Iterable<String[]> data() {
        return Arrays.<String[]> asList(new String[] { "input1.html", "input1.html.selector",
                "input1.html.result" }, new String[] { "input1.html", "input2.html.selector",
                "input2.html.result" });
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
    public TestRegexpSelector(final String resourceFileName, final String selectorFileName,
            final String resultFileName) throws IOException {
        super(resourceFileName, selectorFileName, resultFileName);
    }

    @Override
    public ISelector<String> createSelector() {
        return new RegexpSelector("seletor", getSelectorString());
    }

    @Override
    public String convertStringToParameter(final String par) {
        return par;
    }
}
