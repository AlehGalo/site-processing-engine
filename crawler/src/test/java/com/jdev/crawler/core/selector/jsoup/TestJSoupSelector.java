/**
 * 
 */
package com.jdev.crawler.core.selector.jsoup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.jdev.crawler.core.selector.ISelectorResult;
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
    @Parameters(name = "{index}: file name - {0}, selector file name - {1}, result file name - {2}")
    public static Iterable<String[]> data() {
        return Arrays.<String[]> asList(new String[] { "input1.html", "input1.html.selector",
                "input1.html.result" }, new String[] { "input1.html", "input2.html.selector",
                "input2.html.result" }, new String[] { "input1.html", "input3.html.selector",
                "input3.html.result" });
    }

    /**
     * String limitter.
     */
    private static final String LIMITTER = "@--------";

    /**
     * Resources input file, selectors file, result.
     */
    private final String selectorString;
    private final List<String> resultFileContent;
    private final String contentString;

    /**
     * @param resourceFileName
     *            input file name.
     * @throws IOException
     *             stream exception.
     */
    public TestJSoupSelector(final String resourceFileName, final String selectorFileName,
            final String resultFileName) throws IOException {
        resultFileContent = getResultFileContent(resultFileName);
        contentString = getFileContent(resourceFileName);
        selectorString = getFileContent(selectorFileName);
        validateInput(contentString);
        validateInput(selectorString);
    }

    /**
     * @param input
     *            String to be validated.
     */
    private void validateInput(final String input) {
        Assert.assertTrue("Resource cannot be empty or null", StringUtils.isNotBlank(input));
    }

    /**
     * @throws SelectionException
     *             ex.
     */
    @Test
    public void processSelector() throws SelectionException {
        StringSourceJSoupSelector selector = new StringSourceJSoupSelector("seletor",
                this.selectorString);
        selector.setExtractor(new IJSoupElementExtractor() {

            @Override
            public String getValueFromRecord(final Element element) {
                if (element != null) {
                    return element.text();
                }
                return StringUtils.EMPTY;
            }
        });
        List<ISelectorResult> list = selector.select(contentString);
        List<String> result = new ArrayList<>();
        for (ISelectorResult iSelectorResult : list) {
            result.add(iSelectorResult.getValue());
        }
        Assert.assertEquals(result.size(), resultFileContent.size());
        Assert.assertTrue(ListUtils.subtract(resultFileContent, result).isEmpty());
    }

    /**
     * @param name
     *            file name in the path.
     * @return contentString of the file if found.
     */
    private String getFileContent(final String name) {
        try {
            return IOUtils.toString(this.getClass().getResourceAsStream(name), Charsets.UTF_8);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
            return null;
        }
    }

    /**
     * @param resourceName
     *            name of the file that should be visible at class path.
     * @return file object associated with resource name.
     */
    private File findFile(final String resourceName) {
        return new File(this.getClass().getResource(resourceName).getFile());
    }

    /**
     * @param resultFileName
     *            file name to be processed.
     * @return list of strings from result file. Limitter is a string that
     *         limits the length of the string.
     * @throws IOException
     *             ex.
     */
    private List<String> getResultFileContent(final String resultFileName) throws IOException {
        List<String> result = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        File file = findFile(resultFileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String tempString;
        while ((tempString = reader.readLine()) != null) {
            if (LIMITTER.equals(tempString)) {
                result.add(buffer.toString());
                buffer = new StringBuffer();
            } else {
                buffer.append(tempString);
            }
        }
        reader.close();
        result.add(buffer.toString());
        return result;
    }
}
