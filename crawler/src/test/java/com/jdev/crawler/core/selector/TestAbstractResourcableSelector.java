/**
 * 
 */
package com.jdev.crawler.core.selector;

import static com.jdev.crawler.core.selector.TestUtils.getFileContent;
import static java.nio.charset.Charset.forName;
import static org.apache.commons.collections.ListUtils.subtract;
import static org.apache.commons.io.Charsets.UTF_8;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    protected static final String LIMITTER = "@--------";

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
            final String selectorFileName, final String resultFileName, final String charsetStr)
            throws IOException {
        resultFileContent = getFileResultContent(resultFileName);
        contentString = getContentString(resourceFileName, isEmpty(charsetStr) ? UTF_8
                : forName(charsetStr));
        selectorString = getSelectorString(selectorFileName, UTF_8);
        validateInput(contentString);
        validateInput(selectorString);
    }

    /**
     * @param resultFileName
     *            name of the file.
     * @return list result.
     * @throws IOException
     *             exception.
     */
    protected List<String> getFileResultContent(final String resultFileName) throws IOException {
        return TestUtils.getFileContentSplitted(resultFileName, LIMITTER, this);
    }

    /**
     * @param resourceFileName
     *            file name.
     * @param charset
     * @return content.
     */
    protected String getContentString(final String resourceFileName, final Charset charset) {
        return getFileContent(resourceFileName, this, charset);
    }

    /**
     * @param resourceFileName
     *            resource file name.
     * @param charset
     * @return
     */
    protected String getSelectorString(final String resourceFileName, final Charset charset) {
        return getContentString(resourceFileName, charset);
    }

    /**
     * @param input
     *            String to be validated.
     */
    protected final static void validateInput(final String input) {
        Assert.assertTrue("Resource cannot be empty or null", isNotBlank(input));
    }

    /**
     * @throws SelectionException
     *             ex.
     */
    @Test
    public void processSelector() throws SelectionException {
        ISelector<T> selector = createSelector();
        Assert.assertNotNull("Create selector first", selector);
        Collection<ISelectorResult> list = selector.select(convertStringToParameter(contentString));
        List<String> result = new ArrayList<>();
        for (ISelectorResult iSelectorResult : list) {
            result.add(iSelectorResult.getValue());
        }
        assertEquals("Selector result differs from the expected", resultFileContent.size(),
                result.size());
        assertEquals("Subtraction error. Not equal values met.",
                subtract(resultFileContent, result).size(), 0);
    }

    /**
     * @return selector to be tested.
     */
    public abstract ISelector<T> createSelector();

    /**
     * Converting string parameter to exptected T.
     * 
     * @param par
     *            parameter.
     * @return object.
     */
    public abstract T convertStringToParameter(final String par);

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
