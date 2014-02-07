/**
 * 
 */
package com.jdev.crawler.core.selector.jsoup;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.SelectorResult;
import com.jdev.crawler.exception.SelectionException;

/**
 * @author Aleh
 * 
 */
public class StringSourceJSoupSelector extends AbstractJSoupSelector<String> {

    /**
     * @param selector
     * @param name
     */
    public StringSourceJSoupSelector(final String selector, final String name) {
        super(selector, name);
    }

    @Override
    public List<ISelectorResult> selectValues(final String content) throws SelectionException {
        List<ISelectorResult> result = new ArrayList<>();
        if (StringUtils.isNotBlank(content)) {
            Document document = createDocument(content);
            Elements elements = document.select(getSelector());
            if (!elements.isEmpty()) {
                for (Element element : elements) {
                    result.add(new SelectorResult(getName(), null));
                    // TODO:implement it.
                }
            }
        }
        return result;
    }
}
