/**
 *
 */
package com.jdev.crawler.core.selector.complex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.exception.RegexpSelectionException;
import com.jdev.crawler.exception.SelectionException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh Return all elements from the list that are placed under indexes
 *         otherwise selection exception is thrown. Note that income selector
 *         should return collection of like in out elements.
 */
public class SelectiveValuesSelector<T> implements ISelector<T> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectiveValuesSelector.class);

    /**
     * Index of selected value from the list of non empty indexes.
     */
    private final Set<Integer> indexSet;

    /**
     * Selector to be processed.
     */
    private ISelector<T> selector;

    /**
     * @param selector
     *            object.
     * @param indexOfElements
     *            index of elements.
     */
    public SelectiveValuesSelector(final ISelector<T> selector, final int indexOfElements) {
        this(selector, new int[] { indexOfElements });
    }

    /**
     * @param name
     * @param selector
     * @param indexOfElements
     */
    public SelectiveValuesSelector(final ISelector<T> selector, final int[] indexOfElements) {
        Assert.isTrue(ArrayUtils.isNotEmpty(indexOfElements));
        Assert.notNull(selector);
        indexSet = new HashSet<Integer>(Arrays.<Integer> asList(ArrayUtils
                .toObject(indexOfElements)));
        this.selector = selector;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.crawler.core.selector.regexp.RegexpSelector#selectValues(java
     * .lang.String)
     */
    @Override
    public List<ISelectorResult> select(final T cont) throws SelectionException {
        final Collection<ISelectorResult> collection = selector.select(cont);
        final List<ISelectorResult> selectionResult = new ArrayList<ISelectorResult>();
        ISelectorResult[] array = collection.toArray(new ISelectorResult[collection.size()]);
        for (final Integer index : indexSet) {
            if (collection.size() <= index || index < 0) {
                throw new RegexpSelectionException(String.format(
                        "Index of requested regexp is out of result size. Index=%d, size=%d",
                        index, collection.size()));
            }
            ISelectorResult result = array[index];
            selectionResult.add(result);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Selected name= {} value = {}", result.getName(), result.getValue());
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Selected number of records {}", selectionResult.size());
        }
        return selectionResult;
    }
}
