package com.sswf.desti.extractor.info;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.ObjectSet;
import com.google.common.collect.Maps;
import com.sswf.desti.domain.TargetWebSite;
import com.sswf.desti.report.CsvReportGeneric;

import edu.stanford.nlp.util.StringUtils;

/**
 * @author Alexey Grigorev
 */
public class Runner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    public void run() throws Exception {
        EmbeddedObjectContainer db4o = Db4oEmbedded.openFile("restaurants-raw-11.db");
        ObjectSet<WebsiteContent> query = db4o.query(WebsiteContent.class);

        Map<String, TargetWebSite> index = readWebsiteDescriptors();

        OutputStream csvStream = FileUtils.openOutputStream(new File("timetables.csv"));
        CsvReportGeneric report = new CsvReportGeneric(csvStream);

        for (WebsiteContent wc : query) {
            LOGGER.info("website: {}", wc.getUrl());
            for (WebpageRawContent cont : wc.getPages()) {

                LOGGER.info("page: {}", cont.getUrl());

                // MenuExtractor extractor = new MenuExtractor(wc, cont);
                // List<MenuItem> menuItems = extractor.extract();

            }

            TimetableExtractor timetableExtractor = new TimetableExtractor(wc);
            Set<String> result = timetableExtractor.find();
            LOGGER.info("timetable: for {} found {}", wc.getUrl(), result);

            TargetWebSite webSiteDesc = index.get(wc.getUrl());
            report.addLine(webSiteDesc.getId(), webSiteDesc.getUrl(), webSiteDesc.getPoi(),
                    StringUtils.join(result, ","));

            LOGGER.info("");
            LOGGER.info("");
            LOGGER.info("");
        }

        report.close();
        db4o.close();
    }

    private static Map<String, TargetWebSite> readWebsiteDescriptors() throws IOException {
        Map<String, TargetWebSite> index = Maps.newHashMap();
        List<String> ids = FileUtils.readLines(new File("ids.txt"));
        for (String lins : ids) {
            String[] split = lins.split("\t");
            TargetWebSite site = new TargetWebSite(split[0], split[1], split[2]);
            index.put(site.getUrl(), site);
        }
        return index;
    }

    public static void main(String[] args) throws Exception {
        new Runner().run();
    }

}
