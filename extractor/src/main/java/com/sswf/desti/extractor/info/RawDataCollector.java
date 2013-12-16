package com.sswf.desti.extractor.info;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.google.common.collect.Sets;
import com.jdev.crawler.observer.IObserver;
import com.sswf.desti.domain.Information;
import com.sswf.desti.domain.adapter.IContent;
import com.sswf.desti.extractor.info.graph.WebLink;
import com.sswf.desti.spider.core.Spider;

/**
 * @author Alexey Grigorev
 */
public class RawDataCollector {

    private static final Logger LOGGER = LoggerFactory.getLogger(RawDataCollector.class);

    public void run() throws IOException {
        EmbeddedObjectContainer db4o = Db4oEmbedded.openFile("restaurants-raw-11.db");

        String file = "restaurants.txt";
        Set<String> urls = Sets.newLinkedHashSet(FileUtils.readLines(new File(file), "UTF-8"));

        for (String url : urls) {
            LOGGER.info("processing {}...", url);
            Information inf = new Information(url);
            Spider spider = new Spider(inf);

            final WebsiteContent websiteContent = new WebsiteContent(url);

            spider.addListener(new IObserver<IContent>() {
                @Override
                public void onAction(IContent page) {
                    LOGGER.debug("captured {}...", page.getUrl());
                    Set<WebLink> links = extractLinks(page);
                    websiteContent.addPage(new WebpageRawContent(page), links);
                }

                private Set<WebLink> extractLinks(IContent page) {
                    Set<WebLink> result = new LinkedHashSet<>();

                    Set<IContent> outcomingLinks = page.getOutcomingLinks();
                    for (IContent link : outcomingLinks) {
                        result.add(new WebLink(page.getUrl(), link.getUrl(), link.getText()));
                    }

                    return result;
                }
            });

            spider.collect();

            db4o.store(websiteContent);
            db4o.commit();
        }

        LOGGER.info("DONE");
        db4o.close();
    }

    public static void main(String[] args) throws IOException {
        new RawDataCollector().run();
    }
}
