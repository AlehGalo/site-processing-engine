package com.jdev.crawler.core.step;

import org.apache.http.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.exception.SelectionException;

public class DefaultValidator implements IValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultValidator.class);

    private final ISelector<String>[] selector;

    public DefaultValidator(final ISelector<String>[] selector) {
        this.selector = selector;
    }

    @Override
    public boolean validate(final byte[] content) {
        final String contentString = new String(content, Consts.UTF_8);
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
