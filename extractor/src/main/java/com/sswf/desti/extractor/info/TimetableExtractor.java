package com.sswf.desti.extractor.info;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sswf.desti.extractor.info.cleaner.Cleaner;
import com.sswf.desti.text.Token;
import com.sswf.desti.text.Tokenizer;

/**
 * @author Alexey Grigorev
 */
public class TimetableExtractor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimetableExtractor.class);

    private final WebsiteContent website;
    private final Set<String> res = new HashSet<>();

    public TimetableExtractor(WebsiteContent website) {
        this.website = Validate.notNull(website);
    }

    public Set<String> find() {
        for (WebpageRawContent page : website.getPages()) {
            Document dirty = Jsoup.parse(page.getRawHtml());
            Document clean = Cleaner.clean(dirty);

//            Document clean = dirty;
            clean.select("a").unwrap();
            
            Element body = clean.body();
            if (body == null) {
                LOGGER.warn("web page {} contains no body", page.getUrl());
                continue;
            }

            dfs(body);
        }

        return res;
    }

    private void dfs(Element element) {
        Elements children = element.children();
        String text = element.text();
        if (text.length() > 300) { // ?
            for (Element child : children) {
                dfs(child);
            }
            return;
        }

        List<String> words = Tokenizer.INSTANCE.splitOnWordsWithoutPunctuation(text);
        if (words.size() < 4) {
            // not likely something interesting
            return;
        }

        List<Token> interestingTokens = DateTimeUtils.findTimetableKeywords(text);
        double ratio = keywordsToWordsRatio(interestingTokens, words);

        if (ratio < 0.5) {
            for (Element child : children) {
                dfs(child);
            }
            return;
        }

        // look ma the timetable!
        res.add(text);
    }

    private static double keywordsToWordsRatio(List<Token> interestingTokens, List<String> words) {
        return (interestingTokens.size() * 1.0) / words.size();
    }

}
