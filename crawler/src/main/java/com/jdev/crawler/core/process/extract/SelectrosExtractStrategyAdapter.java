/**
 * 
 */
package com.jdev.crawler.core.process.extract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.http.client.CookieStore;

import com.jdev.crawler.core.process.IProcessContext;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.exception.SelectionException;

/**
 * @author Aleh Adapter point to make a decision of what kind of content should
 *         be inserted into selector's select method.
 */
final class SelectrosExtractStrategyAdapter {

    /**
     * List of classes names that are cookie selection based.
     */
    private static final IContentValidator cookieStoreValidator = new CookieContentOfSelectorValidator();

    /**
     * @param selector
     *            object implemented ISelector interface.
     * @param context
     *            of the execution.
     * @param content
     *            procced by selector content.
     * @return result of selection.
     * @throws SelectionException
     *             ex.
     */
    @SuppressWarnings("unchecked")
    public List<ISelectorResult> extractSelectors(final ISelector<?> selector,
            final IProcessContext context, final IEntity content) throws SelectionException {
        List<ISelectorResult> list = new ArrayList<>();
        if (cookieStoreValidator.isContentValid(selector)) {
            list.addAll(processSelectorsWithCookieStoreContent((ISelector<CookieStore>) selector,
                    context));
        } else {
            list.addAll(processSelectorsWithStringContent((ISelector<String>) selector, content));
        }
        return list;
    }

    /**
     * @param selector
     *            object implemented ISelector interface.
     * @param content
     *            procced by selector content.
     * @return result of selection.
     * @throws SelectionException
     *             ex.
     */
    private Collection<ISelectorResult> processSelectorsWithStringContent(
            final ISelector<String> selector, final IEntity content) throws SelectionException {
        if (content == null) {
            return Collections.<ISelectorResult> emptyList();
        }
        return selector.select(new String(content.getContent(), content.getCharset()));
    }

    /**
     * @param selector
     *            object implemented ISelector interface.
     * @param context
     *            of the execution.
     * @return result of selection.
     * @throws SelectionException
     *             ex.
     */
    private Collection<ISelectorResult> processSelectorsWithCookieStoreContent(
            final ISelector<CookieStore> selector, final IProcessContext context)
            throws SelectionException {
        return selector.select(context.getCookieStore());
    }

}
