package com.sswf.desti.extractor.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.output.StringBuilderWriter;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.sswf.desti.domain.price.Price;

/**
 * @author Aleh Abstract class for extractors test. Html file is input and xml
 *         file is output. Html and xml files should be placed at classpath.
 */
public abstract class AbstractHtmlExtractionTestXml extends XMLTestCase {

    /**
     * Input html file.
     */
    private String htmlContent;

    /**
     * Result xml file.
     */
    private String resultContent;

    /**
     * @param htmlFile
     *            html file resource.
     * @param xmlFile
     *            xml file resource.
     */
    public AbstractHtmlExtractionTestXml(final String htmlFile, final String xmlFile) {
        initFiles(htmlFile, xmlFile);
    }

    @Override
    @Before
    public void setUp() {
        XMLUnit.setIgnoreAttributeOrder(true);
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreComments(true);
    }

    /**
     * @param htmlFile
     * @param xmlFile
     */
    protected void initFiles(final String htmlFile, final String xmlFile) {
        htmlContent = getBufferedReaderContent(createBufferedReader(htmlFile));
        resultContent = getBufferedReaderContent(createBufferedReader(xmlFile));

    }

    /**
     * @param fileName
     *            name of the file.
     * @return buffered reader for resource.
     */
    private BufferedReader createBufferedReader(final String fileName) {
        InputStream is = getClass().getResourceAsStream(fileName);
        if (is == null) {
            Assert.fail("File is not found: " + fileName);
        }
        return new BufferedReader(new InputStreamReader(is));
    }

    /**
     * @param br
     *            reader.
     * @return content string or empty string.
     */
    private String getBufferedReaderContent(final BufferedReader br) {
        StringBuilder sb = new StringBuilder();
        String content;
        try {
            while ((content = br.readLine()) != null) {
                sb.append(content);
            }
        } catch (IOException ex) {
            Assert.fail(ex.getMessage());
        }
        return sb.toString();
    }

    /**
     * @return set of prices.
     */
    public abstract Set<Price> extractHtmlFile(final String htmlContent);

    /**
     * Test.
     * 
     * @throws IOException
     * @throws SAXException
     */
    @Test
    public void compareExtractionResultWithXmlContent() throws SAXException, IOException {
        Set<Price> setOfPrices = extractHtmlFile(htmlContent);
        PriceAccumulationBean bean = new PriceAccumulationBean();
        StringBuilderWriter sbw = new StringBuilderWriter();
        bean.setPrices(setOfPrices);
        try {
            JAXBContext jc = JAXBContext.newInstance(PriceAccumulationBean.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.marshal(bean, sbw);
        } catch (JAXBException e) {
            Assert.fail(e.getMessage());
        }
        assertXMLEqual(sbw.toString(), resultContent);
    }
}
