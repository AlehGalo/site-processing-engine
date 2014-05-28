package com.jdev.crawler.core.selector.regexp;

import java.io.IOException;
import java.util.Arrays;

import org.junit.runners.Parameterized.Parameters;

import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.SelectUnit;
import com.jdev.crawler.core.selector.TestAbstractResourcableSelector;

public class TestRegexpSelector extends TestAbstractResourcableSelector<String> {

    /**
     * @return Iterable resources.
     */
    @Parameters(name = "{index}: file name - {0}, selector file name - {1}, result file name - {2}")
    public static Iterable<String[]> data() {
        return Arrays.<String[]> asList(new String[] { "input1.html", "input1.html.selector",
                "input1.html.result", null }, new String[] { "input1.html", "input2.html.selector",
                "input2.html.result", null }, new String[] { "input2.html",
                "input2_1.html.selector", "input2_1.html.result", null }, new String[] {
                "input3.html", "input3.html.selector", "input3.html.result", null }, new String[] {
                "input4.html", "input4.html.selector", "input4.html.result", null });
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
    public TestRegexpSelector(final String resourceFileName, final String selectorFileName,
            final String resultFileName, final String charsetName) throws IOException {
        super(resourceFileName, selectorFileName, resultFileName, charsetName);
    }

    @Override
    public ISelector<String> createSelector() {
        return new RegexpSelector(new SelectUnit("seletor", getSelectorString()));
    }

    @Override
    public String convertStringToParameter(final String par) {
        return par;
    }
}
