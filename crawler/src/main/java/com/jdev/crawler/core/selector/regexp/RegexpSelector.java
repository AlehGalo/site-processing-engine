/**
 *
 */
package com.jdev.crawler.core.selector.regexp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.RequestReservedWord;
import com.jdev.crawler.core.selector.SelectorResult;
import com.jdev.crawler.exception.RegexpSelectionException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh Non empty list will be the result of selectValues or exception.
 */
public class RegexpSelector implements ISelector<String> {

    /**
     * String that contains Regexp for selection.
     */
    private final String selector;

    /**
     * Name of the parameter.
     */
    private final String name;

    /**
     * @param name
     * @param selector
     */
    public RegexpSelector(final String name, final String selector) {
        Assert.hasLength(selector);
        Assert.hasLength(name);
        this.selector = selector;
        this.name = name;
    }

    /**
     * @param word
     * @param selector
     */
    public RegexpSelector(final RequestReservedWord word, final String selector) {
        Assert.hasLength(selector);
        Assert.notNull(word);
        this.selector = selector;
        this.name = word.getWord();
    }

    /**
     * Return value is a non-empty list. If list is empty then
     * {@link RegexpSelectionException} will be thrown. It's important to look
     * after selection results.
     */
    @Override
    public List<ISelectorResult> select(final String cont) throws RegexpSelectionException {
        final List<ISelectorResult> list = new ArrayList<ISelectorResult>();
        if (!StringUtils.isEmpty(cont) && !StringUtils.isEmpty(selector)) {
            final Pattern pattern = Pattern.compile(selector);
            final Matcher m = pattern.matcher(cont);
            while (m.find()) {
                final String value = m.group(1);
                if (StringUtils.isEmpty(value)) {
                    throw new RegexpSelectionException("Selected value is null or empty.", name,
                            selector);
                }
                list.add(new SelectorResult(name, value));
            }
        }
        if (list.isEmpty()) {
            throw new RegexpSelectionException(name, selector);
        }
        return list;
    }

    /**
     * @return the selector.
     */
    protected final String getSelector() {
        return selector;
    }

    /**
     * @return the name.
     */
    protected final String getName() {
        return name;
    }
}