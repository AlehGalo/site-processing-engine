/**
 * 
 */
package com.jdev.crawler.core.selector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;

/**
 * @author Aleh Utils class for working with resource files.
 */
public final class TestUtils {

    /**
     * Hide public constructor.
     */
    private TestUtils() {
    }

    /**
     * @param resultFileName
     *            file name to be processed.
     * @param limitter
     *            string limitter.
     * @param relativeObj
     *            object whose relative path will be used.
     * @return list of strings from result file. Limitter is a string that
     *         limits the length of the string.
     * @throws IOException
     */
    public static List<String> getFileContentSplitted(final String resultFileName,
            final String limitter, final Object relativeObj) throws IOException {
        com.jdev.crawler.util.Assert.hasLength(resultFileName);
        com.jdev.crawler.util.Assert.hasLength(limitter);
        Assert.assertNotNull(relativeObj);
        List<String> result = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        File file = findFile(resultFileName, relativeObj);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        try {
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                if (limitter.equals(tempString)) {
                    result.add(buffer.toString());
                    buffer = new StringBuffer();
                } else {
                    buffer.append(tempString);
                }
            }
        } finally {
            reader.close();
        }
        result.add(buffer.toString());
        return result;
    }

    /**
     * @param resourceName
     *            name of the file that should be visible at class path.
     * @param obj
     *            relative object for resource find.
     * @return file object associated with resource name.
     */
    private static File findFile(final String resourceName, final Object obj) {
        URL url = obj.getClass().getResource(resourceName);
        assertFile(url, resourceName);
        return new File(url.getFile());
    }

    /**
     * @param resource
     *            URL or file.
     * @param name
     *            of the file resource.
     */
    public static void assertFile(final Object resource, final String name) {
        Assert.assertNotNull("File not found [" + name + "]", resource);
    }

    /**
     * @param name
     *            file name in the path.
     * @param obj
     *            relative object.
     * @return contentString of the file if found.
     */
    public static String getFileContent(final String name, final Object obj, final Charset charset) {
        try {
            InputStream is = obj.getClass().getResourceAsStream(name);
            assertFile(is, name);
            return IOUtils.toString(is, charset);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
            return null;
        }
    }
}
