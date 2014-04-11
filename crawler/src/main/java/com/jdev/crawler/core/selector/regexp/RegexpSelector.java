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

import com.jdev.crawler.core.selector.ISelectUnit;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
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
    private final ISelectUnit selectUnit;

    /**
     * @param unit
     */
    public RegexpSelector(final ISelectUnit unit) {
        Assert.notNull(unit);
        this.selectUnit = unit;
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
        final Pattern pattern = Pattern.compile(getSelectUnit().getSelector());
        final Matcher m = pattern.matcher(cont);
        final String name = getSelectUnit().getName();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Selector name = {} selector = {}", name, getSelectUnit().getSelector());
        }
        while (m.find()) {
            if (m.groupCount() < 1) {
                throw new RegexpSelectionException(
                        "Regexp selection failed. At least 1 group is required.");
            }
            final String value = m.group(1);
            if (isEmpty(value)) {
                throw new RegexpSelectionException(name, getSelectUnit().getSelector());
            }
            LOGGER.debug("Found item name = {} value = {}", name, value);
            list.add(new SelectorResult(name, value));
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Results found {}", list.size());
        }
        return list;
    }

    /**
     * @return select unit.
     */
    protected ISelectUnit getSelectUnit() {
        return this.selectUnit;
    }
}