/**
 *
 */
package com.jdev.crawler.core.selector.regexp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.exception.RegexpSelectionException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh Return all elements from the list that are placed under indexes
 *         otherwise selection exception is thrown.
 */
public class RegexpSelectorParameterized extends RegexpSelector {

    /**
     * Index of selected value from the list of non empty indexes.
     */
    private final Set<Integer> indexSet;

    /**
     * @param name
     * @param selector
     * @param indexOfElements
     */
    public RegexpSelectorParameterized(final String name,
	    final String selector, final int indexOfElements) {
	this(name, selector, new int[] { indexOfElements });
    }

    /**
     * @param name
     * @param selector
     * @param indexOfElements
     */
    public RegexpSelectorParameterized(final String name,
	    final String selector, final int[] indexOfElements) {
	super(name, selector);
	Assert.isTrue(ArrayUtils.isNotEmpty(indexOfElements));
	indexSet = new HashSet<Integer>(Arrays.<Integer> asList(ArrayUtils
		.toObject(indexOfElements)));

    }

    @Override
    public List<ISelectorResult> selectValues(final Object cont)
	    throws RegexpSelectionException {
	final List<ISelectorResult> list = super.selectValues(cont);
	final List<ISelectorResult> selectionResult = new ArrayList<ISelectorResult>();
	for (final Integer index : indexSet) {
	    if (list.size() <= index || index < 0) {
		throw new RegexpSelectionException(
			getName(),
			getSelector(),
			String.format(
				"Index of requested regexp is out of result size. Index=%d, size=%d",
				index, list.size()));
	    }
	    selectionResult.add(list.get(index));
	}
	return selectionResult;
    }

}
