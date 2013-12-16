package com.sswf.desti.extractor.common;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.sswf.desti.domain.adapter.ContentUrl;
import com.sswf.desti.domain.price.Price;

/**
 * @author Aleh Price extractor test.
 */
@RunWith(Parameterized.class)
public class TestPriceExtractor extends AbstractHtmlExtractionTestXml {

    /**
     * @return data for test.
     */
    @Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "Venice_Pizza_House.html", "Venice_Pizza_House.xml" },
                { "Marriott Little Rock _ Courtyard Little Rock Downtown.html",
                        "Marriott Little Rock _ Courtyard Little Rock Downtown.xml" } });
    }

    /**
     * @param htmlFileName
     *            html file.
     * @param xmlFileName
     *            xml file.
     */
    public TestPriceExtractor(final String htmlFileName, final String xmlFileName) {
        super(htmlFileName, xmlFileName);
    }

    @Override
    public Set<Price> extractHtmlFile(final String htmlContent) {
        PriceExtractor extractor = new PriceExtractor();
        Set<String> set = extractor.extract(new ContentUrl(StringUtils.EMPTY, htmlContent, Jsoup
                .parse(htmlContent).text()));
        Set<Price> resultSet = new LinkedHashSet<>();
        MessageFormat mf = new MessageFormat(PriceExtractor.PRICE_PATTERN);
        for (String priceString : set) {
            Object[] obj = null;
            try {
                obj = mf.parse(priceString);
            } catch (ParseException e) {
                Assert.fail(e.getMessage());
            }
            if (obj == null || obj.length != 2) {
                Assert.fail("Pattern cannot be applied.");
            }
            resultSet.add(new Price((String) obj[0], (String) obj[1]));
        }
        return resultSet;

    }
}
