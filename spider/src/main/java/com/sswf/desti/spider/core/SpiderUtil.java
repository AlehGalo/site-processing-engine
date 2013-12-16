/**
 * 
 */
package com.sswf.desti.spider.core;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.CookieStore;
import org.springframework.util.Assert;

import com.jdev.crawler.builder.CrawlerBuilder;
import com.jdev.crawler.core.Agent;
import com.jdev.crawler.core.FileType;
import com.jdev.crawler.core.HttpClientFactory;
import com.jdev.crawler.core.ICrawler;
import com.jdev.crawler.core.process.ProcessUtils;
import com.jdev.crawler.core.process.handler.MimeType;
import com.jdev.crawler.core.request.BasicRequestBuilder;
import com.jdev.crawler.core.store.FileStorage;
import com.jdev.crawler.core.store.IFileStore;
import com.jdev.crawler.core.store.IFileStoreWritable;
import com.jdev.crawler.core.store.IIndexedItem;
import com.jdev.crawler.core.user.IUserData;
import com.sswf.desti.domain.adapter.IContent;
import com.sswf.desti.spider.core.save.SaveHTMLStreamHandlerIgnoreException;

/**
 * @author Aleh
 * 
 */
public final class SpiderUtil {

    /**
     * Slash.
     */
    private static final String SLASH = "/";

    /**
     * Default file storage name.
     */
    private static final String FILE_STORAGE_NAME = "crawler_file_storage";

    /**
     * Cookie store.
     */
    private static final CookieStore COOKIESTORE;

    /**
     * Request builder.
     */
    private static final BasicRequestBuilder REQUESTBUILDER;

    /**
     * All types accepted for processing.
     */
    private static final MimeType[] ACCEPTED_MIME_TYPES;

    /**
     * User.
     */
    private static final IUserData USER;

    static {
        COOKIESTORE = HttpClientFactory.getCookieStore();
        REQUESTBUILDER = new BasicRequestBuilder();
        ACCEPTED_MIME_TYPES = new MimeType[] { MimeType.HTML };
        USER = DomainUser.getInstance();
    }

    /**
     * Hide public constructor.
     */
    private SpiderUtil() {

    }

    /**
     * 
     */
    public static ICrawler createCrawler(final String url) {
        return new CrawlerBuilder(ProcessUtils.doGet(url, ACCEPTED_MIME_TYPES,
                new SaveHTMLStreamHandlerIgnoreException()), USER)
                .buildClient(HttpClientFactory.createHttpClient(Agent.CHROME_USER_AGENT))
                .buildCookieStore(COOKIESTORE).buildFileStorage(new FileStorage(FILE_STORAGE_NAME))
                .buildRequestBuilder(REQUESTBUILDER).buildStoreMarkup(false).getResult();
    }

    /**
     * @param crawler
     * @return
     */
    public static File getFile(final ICrawler crawler) {
        final Collection<IFileStoreWritable> fileStoreCollection = crawler.getFileStorage()
                .getAllFileStore();
        if (!fileStoreCollection.isEmpty()) {
            final List<IIndexedItem> list = ((IFileStore) fileStoreCollection.toArray()[0])
                    .getItems(FileType.HTML);
            Assert.isTrue(list.size() <= 1);
            if (list.size() != 0) {
                return new File(list.get(0).getFileName());
            }
        }
        return null;
    }

    /**
     * @param urls
     * @param host
     * @return
     */
    public static Set<IContent> getUrlsOfTheSameDomain(final Set<IContent> urls, final String host) {
        final Set<IContent> set = new HashSet<>();
        if (CollectionUtils.isNotEmpty(urls)) {
            for (final IContent url : urls) {
                if (isTheSameDomain(url.getUrl(), host)) {
                    set.add(url);
                }
            }
        }
        return set;
    }

    /**
     * @param source
     * @return string.
     */
    public static String getTheSameWithoutLastSlash(final String source) {
        return StringUtils.endsWith(source, SLASH) ? StringUtils.removeEnd(source, SLASH).trim()
                : source.trim();
    }

    /**
     * @param url
     * @param host
     * @return
     */
    public static boolean isTheSameDomain(final String url, final String host) {
        return StringUtils.contains(url, host);
    }

}
