/**
 * 
 */
package com.jdev.crawler.core.selector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.jdev.crawler.exception.SelectionException;

/**
 * @author Aleh
 * 
 */
@RunWith(Parameterized.class)
public abstract class TestAbstractResourcableSelector<T> {

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
    public TestAbstractResourcableSelector(final String resourceFileName,
            final String selectorFileName, final String resultFileName) throws IOException {
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
    protected final static void validateInput(final String input) {
        Assert.assertTrue("Resource cannot be empty or null", StringUtils.isNotBlank(input));
    }

    /**
     * @throws SelectionException
     *             ex.
     */
    @Test
    public void processSelector() throws SelectionException {
        ISelector<T> selector = createSelectorWithExtractor();
        List<ISelectorResult> list = selector.select(contentString);
        List<String> result = new ArrayList<>();
        for (ISelectorResult iSelectorResult : list) {
            result.add(iSelectorResult.getValue());
        }
        Assert.assertEquals(result.size(), resultFileContent.size());
        Assert.assertTrue(ListUtils.subtract(resultFileContent, result).isEmpty());
    }

    /**
     * @return selector to be tested.
     */
    public abstract ISelector<T> createSelectorWithExtractor();

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

    /**
     * @return the selectorString
     */
    public String getSelectorString() {
        return selectorString;
    }

    /**
     * @return the resultFileContent
     */
    public List<String> getResultFileContent() {
        return resultFileContent;
    }

    /**
     * @return the contentString
     */
    public String getContentString() {
        return contentString;
    }
}
