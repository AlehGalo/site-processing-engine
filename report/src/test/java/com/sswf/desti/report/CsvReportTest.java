package com.sswf.desti.report;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.StringBuilderWriter;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.sswf.desti.domain.Information;
import com.sswf.desti.domain.TargetWebSite;
import com.sswf.desti.domain.social.Social;

/**
 * @author Alexey Grigorev
 */
public class CsvReportTest {

    @Test
    @Ignore
    public void addLine() throws Exception {
        String homeUrl = "http://www.somewebsite.com/";
        String id = "31337";
        String poi = "some cool stuff";
        TargetWebSite website = new TargetWebSite(id, poi, homeUrl);

        Information info = new Information(homeUrl);
        info.setPhoneNumbers(ImmutableSet.of("12345", "23456", "78901"));
        Social social = new Social();
        social.setFacebookUri(new URI("https://facebook.com/somegroup"));
        social.setTwitterUri(new URI("http://twitter.com/twitteract"));
        info.setSocial(social);

        StringBuilderWriter stream = new StringBuilderWriter();
        CsvReport csvReport = new CsvReport(stream);

        csvReport.addLine(website, info);

        csvReport.close();

        InputStream resource = this.getClass().getResourceAsStream("expected.csv");
        String expected = IOUtils.toString(resource);

        assertEquals(expected.trim(), stream.toString().trim());
    }

}
