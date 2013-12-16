package com.jdev.crawler.core.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.Consts;

import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.cookie.CookieSelector;
import com.jdev.crawler.core.selector.cookie.CookieSelectorUnion;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.exception.SelectionException;

/**
 * @author Aleh
 * 
 */
public class DefaultSelectorExtractStrategy implements ISelectorExtractStrategy {

    @Override
    public List<ISelectorResult> extractSelectors(
	    final IProcessContext context, final IStepConfig config,
	    final byte[] content) throws SelectionException {
	final List<ISelectorResult> resultList = new ArrayList<ISelectorResult>();
	if (content != null) {
	    final Collection<ISelector> selectorsList = config.getParameters();
	    if (!CollectionUtils.isEmpty(selectorsList)) {
		for (final ISelector iSelector : selectorsList) {
		    if (iSelector instanceof CookieSelector
			    || iSelector instanceof CookieSelectorUnion) {
			final Collection<? extends ISelectorResult> result = iSelector
				.selectValues(context.getCookieStore());
			resultList.addAll(result);
		    } else {
			final Collection<? extends ISelectorResult> result = iSelector
				.selectValues(new String(content, Consts.UTF_8));
			resultList.addAll(result);
		    }
		}
	    }
	}
	return resultList;
    }
}