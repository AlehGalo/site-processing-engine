package com.sswf.desti.spider.core.extractor;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

import com.sswf.desti.domain.adapter.ContentUrl;
import com.sswf.desti.domain.adapter.IContent;
import com.sswf.desti.domain.validate.URLValidator;
import com.sswf.desti.spider.core.SpiderUtil;
import com.sswf.desti.spider.core.extractor.deprecation.IDeprecated;

/**
 * @author Aleh URL - http://example.com/some/page.html URI - /some/page.html
 * 
 */
// TODO: get it back.
public class LinksExtractor /* implements IExtractor<Set<IContent>, String> */{

    private static final Logger LOGGER = LoggerFactory.getLogger(LinksExtractor.class);

    private static final String URL_SEPARATOR = "/";
    private static final String ANCHOR_CHAR = "#";

    private final Set<String> invalidUrlSet = new HashSet<>();

    private final String domain;
    private final String rootUrl;

    private IDeprecated deprecatedUrl;

    /**
     * Depth or url. If not set all urls will process.
     */
    private IDepth depth;

    /**
     * @param stringUrl
     */
    public LinksExtractor(String stringUrl) {
        URL url = UrlUtils.buildUrl(stringUrl);
        this.domain = UrlUtils.extractDomain(url);
        this.rootUrl = UrlUtils.truncateFromLastSlash(url);
    }

    // TODO: move it to a separate file
    public static class Link {
        private final String href;
        private final String text;

        public Link(String href, String text) {
            this.href = href;
            this.text = text;
        }

        public String getHref() {
            return href;
        }

        public String getText() {
            return text;
        }

        public Link normalized() {
            String link = hrefWithoutAnchor(href);
            link = SpiderUtil.getTheSameWithoutLastSlash(link);
            link = link.toLowerCase(Locale.US);
            return new Link(link, text);
        }

        /**
         * @return link without anchor.
         */
        private static String hrefWithoutAnchor(String href) {
            int lastAnchorSymbolIndex = StringUtils.lastIndexOf(href, ANCHOR_CHAR);
            if (lastAnchorSymbolIndex != -1) {
                return href.substring(0, lastAnchorSymbolIndex);
            }
            return href;
        }

    }

    public Set<Link> extractLinks(String html) {
        Validate.validState(deprecatedUrl != null, "deprecates urls must be set");

        Set<Link> result = new HashSet<>();
        Document doc = Jsoup.parse(html);
        Elements links = doc.getElementsByTag("a");

        for (Element linkNode : links) {
            Link link = extractLinkIfValid(linkNode);
            if (link == null) {
                continue;
            }

            link = link.normalized();
            if (StringUtils.isBlank(link.href)) {
                continue;
            }

            if (!deprecatedUrl.isNotDeprecated(link.href)) {
                LOGGER.debug("Invalid (Deprecated) url {}", link.href);
                invalidUrlSet.add(link.href);
                continue;
            }

            if (depth == null) {
                result.add(link);
                continue;
            }

            if (depth.isDepthAccepted(UrlUtils.buildUrl(link.href))) {
                result.add(link);
            } else {
                LOGGER.info("Invalid (Depth is more then required) url {}", link.href);
                invalidUrlSet.add(link.href);
            }
        }

        return result;

    }

    private Link extractLinkIfValid(Element link) {
        String href = "";
        try {
            String hrefRaw = link.attr("href").trim();
            href = normalizeHref(hrefRaw);
            String text = link.text().trim();

            URI uri = new URI(href);

            if (uri.getHost() == null && href.length() > 1) {
                String uriString = href.startsWith(URL_SEPARATOR) ? domain + href : rootUrl + URL_SEPARATOR
                        + href.trim();
                href = normalizeUri(uriString);
            }

            if (notValidUrl(href)) {
                invalidUrlSet.add(href);
                LOGGER.info("Invalid url '{}'", href);
                return null;
            }
            return new Link(href, text);
        } catch (URISyntaxException | UnsupportedEncodingException ex) {
            invalidUrlSet.add(href);
            LOGGER.info("Invalid url '{}'", href);
            return null;
        }
    }

    private static boolean notValidUrl(String href) {
        return !URLValidator.isValid(href);
    }

    public Set<IContent> extract(String html) {
        // TODO return here link objects
        Set<Link> links = extractLinks(html);
        Set<IContent> res = new HashSet<>();

        for (Link link : links) {
            res.add(new ContentUrl(link.href, "", link.text));
        }

        return res;
    }

    @Deprecated
    public Set<IContent> extractOld(String e) {
        Validate.validState(deprecatedUrl != null, "deprecates urls must be set");

        Set<IContent> result = new HashSet<>();
        Document doc = Jsoup.parse(e);
        Elements links = doc.getElementsByTag("a");

        for (Element link : links) {
            String href = null;
            String text = null;
            URI uri = null;
            try {
                href = normalizeHref(link.attr("href").trim());
                text = link.text().trim();
                uri = new URI(href);
                if (null == uri.getHost() && href.length() > 1) {
                    href = normalizeUri(href.startsWith(URL_SEPARATOR) ? domain + href : rootUrl
                            + URL_SEPARATOR + href.trim());
                }
                if (!new URLValidator(href).validate()) {
                    invalidUrlSet.add(href);
                    LOGGER.info("Invalid url {}", href);
                    href = "";
                }
            } catch (final URISyntaxException | UnsupportedEncodingException ex) {
                invalidUrlSet.add(href);
                LOGGER.info("Invalid url {}", href);
                href = "";
            }
            href = cleanAnchorFromHref(href);
            href = SpiderUtil.getTheSameWithoutLastSlash(href);
            if (deprecatedUrl.isNotDeprecated(href)) {
                if (StringUtils.isNotBlank(href)) {
                    try {
                        if (depth != null) {
                            if (depth.isDepthAccepted(new URL(href))) {
                                result.add(new ContentUrl(href, e, text));
                            } else {
                                LOGGER.info("Invalid (Depth is more then required) url {}", href);
                                invalidUrlSet.add(href);
                            }
                        } else {
                            result.add(new ContentUrl(href, e, text));
                        }
                    } catch (MalformedURLException ex) {

                    }
                }
            } else {
                LOGGER.info("Invalid (Deprecated) url {}", href);
                invalidUrlSet.add(href);
            }
        }
        return result;
    }

    private static String cleanAnchorFromHref(String href) {
        int lastAnchorSymbolIndex = StringUtils.lastIndexOf(href, ANCHOR_CHAR);
        if (lastAnchorSymbolIndex != -1) {
            return href.substring(0, lastAnchorSymbolIndex);
        }
        return href;
    }

    /**
     * Normalize unwise symbols and so on. unwise = "{" | "}" | "|" | "\" | "^" | "[" | "]" | "`"
     * 
     * @return transformed string.
     * @throws URISyntaxException ex.
     * @throws UnsupportedEncodingException
     */
    private static String normalizeHref(final String href) throws URISyntaxException,
            UnsupportedEncodingException {
        if (StringUtils.isNotBlank(href)) {
            return UriComponentsBuilder.fromUriString(href).build().encode().toString();
        }
        return href;
    }

    private static String normalizeUri(final String uriString) throws URISyntaxException {
        return new URI(uriString).normalize().toString();
    }

    public Set<String> getInvalidUrlSet() {
        return invalidUrlSet;
    }

    public final IDeprecated getDeprecated() {
        return deprecatedUrl;
    }

    public final void setDeprecated(final IDeprecated deprecated) {
        this.deprecatedUrl = deprecated;
    }

    public final IDepth getDepth() {
        return depth;
    }

    public final void setDepth(final IDepth depth) {
        this.depth = depth;
    }
}