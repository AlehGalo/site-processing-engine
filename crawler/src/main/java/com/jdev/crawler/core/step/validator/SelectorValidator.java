package com.jdev.crawler.core.step.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.exception.SelectionException;

/**
 * @author Aleh Default validator implementation. Validation will be accepted as
 *         far as all selectors will accepted.
 */
public class SelectorValidator implements IValidator {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectorValidator.class);

    /**
     * String selectors.
     */
    private final ISelector<String>[] selector;

    /**
     * @param selector
     *            elements that should be all ok for validation pass.
     */
    @SafeVarargs
    public SelectorValidator(final ISelector<String>... selector) {
        this.selector = selector;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.crawler.core.step.IValidator#validate(com.jdev.crawler.core.
     * process.model.IEntity)
     */
    @Override
    public boolean validate(final IEntity entity) {
        final String contentString = new String(entity.getContent(), entity.getCharset());
        for (final ISelector<String> sel : selector) {
            try {
                if (sel.select(contentString).isEmpty()) {
                    return false;
                }
            } catch (final SelectionException e) {
                LOGGER.info(e.getLocalizedMessage());
                return false;
            }
        }
        return true;
    }
}
