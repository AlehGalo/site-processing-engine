/**
 *
 */
package com.jdev.crawler.core.selector.regexp;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RegexpSelector.class);

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
        if (isEmpty(cont)) {
            throw new RegexpSelectionException("Content cannot be null or empty for selector");
        }
        final List<ISelectorResult> list = new ArrayList<>();
        final Pattern pattern = Pattern.compile(selector);
        final Matcher m = pattern.matcher(cont);
        while (m.find()) {
            if (m.groupCount() < 1) {
                throw new RegexpSelectionException(
                        "Regexp selection failed. At least 1 group is required.");
            }
            final String value = m.group(1);
            if (isEmpty(value)) {
                throw new RegexpSelectionException(name, selector);
            }
            LOGGER.debug("Found item name = {} value = {}" + name, value);
            list.add(new SelectorResult(name, value));
        }
        LOGGER.debug("Selection result found {} items" + list.size());
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