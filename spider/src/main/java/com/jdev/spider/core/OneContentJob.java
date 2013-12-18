package com.jdev.spider.core;

import static com.jdev.spider.core.SpiderUtil.getUrlsOfTheSameDomain;
import static com.jdev.spider.core.extractor.deprecation.DeprecationFactory.createDeprecationRules;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.jsoup.Jsoup.parse;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.ICrawler;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.crawler.observer.IObserver;
import com.jdev.crawler.observer.IObserverable;
import com.jdev.domain.adapter.ContentUrl;
import com.jdev.domain.adapter.IContent;
import com.jdev.domain.statistics.Statistics;
import com.jdev.spider.core.coordinator.IURLCoordinator;
import com.jdev.spider.core.extractor.DepthOfHref;
import com.jdev.spider.core.extractor.LinksExtractor;

/**
 * @author Aleh
 */
class OneContentJob implements IObserverable<IContent> {

    private final static Logger LOGGER = LoggerFactory.getLogger(OneContentJob.class);

    private final Statistics statistics;
    private final AtomicInteger numberOfPagesScanned;
    private final String host;

    private final IObserverable<IContent> obs;

    private final IURLCoordinator<IContent> urlCoordinator;

    private final Set<IContent> setOfNotDomainedUrls;

    private int maxUrlDepth = -1;

    public OneContentJob(final Statistics stat, final AtomicInteger pagesScanned,
            final String host, final IObserverable<IContent> obs,
            final IURLCoordinator<IContent> urlCoordinator, final Set<IContent> setOfNotDomainedUrls) {
        this.statistics = stat;
        this.numberOfPagesScanned = pagesScanned;
        this.host = host;
        this.obs = obs;
        this.urlCoordinator = urlCoordinator;
        this.setOfNotDomainedUrls = setOfNotDomainedUrls;
    }

    public OneContentJob(final Statistics stat, final AtomicInteger pagesScanned,
            final String host, final IObserverable<IContent> obs,
            final IURLCoordinator<IContent> urlCoordinator,
            final Set<IContent> setOfNotDomainedUrls, final int maxUrlDepth) {
        this(stat, pagesScanned, host, obs, urlCoordinator, setOfNotDomainedUrls);
        this.maxUrlDepth = maxUrlDepth;
    }

    void atomicContentExecution(final IContent url) {
        final ICrawler crawler = SpiderUtil.createCrawler(url.getUrl());

        try {
            crawler.collect();
        } catch (final CrawlerException ce) {
            statistics.addInvalidUrl(url.getUrl());
            LOGGER.error("error when crawling {}", url.getUrl(), ce);
            return;
        }

        applyDownloadedFile(SpiderUtil.getFile(crawler), url);
    }

    private void applyDownloadedFile(final File file, final IContent url) {
        if (file == null) {
            return;
        }

        try {
            tryProcessDownloadedFile(file, url);
        } catch (final IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        if (!file.delete()) {
            LOGGER.error("Error deleting file {}", file.getAbsolutePath());
        }
    }

    private void tryProcessDownloadedFile(final File file, final IContent url) throws IOException {
        String rawContent = readFileToString(file);
        statistics.increaseData(rawContent.length());

        String currentPageUrl = url.getUrl();
        LinksExtractor linkExtractor = createLinksExtractor(currentPageUrl);

        Set<IContent> allExtractedLinks = linkExtractor.extract(rawContent);
        statistics.addInvalidUrls(linkExtractor.getInvalidUrlSet());

        Set<IContent> correctUrls = getUrlsOfTheSameDomain(allExtractedLinks, host);
        urlCoordinator.addAllUrls(correctUrls);
        allExtractedLinks.removeAll(correctUrls);

        setOfNotDomainedUrls.addAll(allExtractedLinks);

        String extractedText = parse(rawContent).text();
        ContentUrl contentUrl = new ContentUrl(currentPageUrl, rawContent, extractedText);
        contentUrl.setOutcomingLinks(correctUrls);
        notifyListeners(contentUrl);

        statistics.incrementTotalLinksCount();
        numberOfPagesScanned.incrementAndGet();
    }

    private LinksExtractor createLinksExtractor(final String currentPageUrl) {
        LinksExtractor linkExtractor = new LinksExtractor(currentPageUrl);
        linkExtractor.setDeprecated(createDeprecationRules());
        if (maxUrlDepth != -1) {
            linkExtractor.setDepth(new DepthOfHref(maxUrlDepth));
        }
        return linkExtractor;
    }

    @Override
    public boolean addListener(final IObserver<IContent> listener) {
        return obs.addListener(listener);
    }

    @Override
    public boolean deleteListener(final IObserver<IContent> listener) {
        return obs.deleteListener(listener);
    }

    @Override
    public void notifyListeners(final IContent event) {
        obs.notifyListeners(event);
        event.resetStrings();
    }
}
