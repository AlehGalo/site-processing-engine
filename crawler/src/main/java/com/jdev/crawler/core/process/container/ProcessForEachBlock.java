/**
 * 
 */
package com.jdev.crawler.core.process.container;

import static com.jdev.crawler.util.Assert.notNull;
import static org.apache.commons.lang3.StringUtils.stripToEmpty;

import java.util.Collection;
import java.util.List;

import com.jdev.crawler.core.process.IProcess;
import com.jdev.crawler.core.process.IProcessSession;
import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.process.model.TransferEntity;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.exception.CrawlerException;

/**
 * @author Aleh
 * 
 */
public class ProcessForEachBlock<T extends IProcess> extends AbstractBaseProcessContainer<T> {

    /**
     * Selection of items.
     */
    private final ISelector<String> selector;

    /**
     * @param elements
     * @param selector
     */
    public ProcessForEachBlock(final List<T> elements, final ISelector<String> selector) {
        super(elements);
        notNull(selector);
        this.selector = selector;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.crawler.core.process.IProcess#process(com.jdev.crawler.core.
     * process.IProcessSession, com.jdev.crawler.core.process.model.IEntity,
     * com.jdev.crawler.core.process.extract.ISelectorExtractStrategy)
     */
    @Override
    public IEntity process(final IProcessSession session, final IEntity content,
            final ISelectorExtractStrategy extractStrategy) throws CrawlerException {
        Collection<ISelectorResult> collection = selector.select(new String(content.getContent(),
                content.getCharset()));
        TransferEntity entity = new TransferEntity(content.getMimeType(),
                content.getContentFileRef(), content.getCharset(), new byte[] {},
                content.getStatusCode());
        for (ISelectorResult iSelectorResult : collection) {
            entity.setContent(stripToEmpty(iSelectorResult.getValue()).getBytes());
            processSection(session, entity, extractStrategy);
        }
        return content;
    }

    /**
     * @param selectionResult
     * @throws CrawlerException
     */
    private void processSection(final IProcessSession session, final IEntity content,
            final ISelectorExtractStrategy extractStrategy) throws CrawlerException {
        List<T> listOfItems = getElements();
        for (T t : listOfItems) {
            t.process(session, content, extractStrategy);
        }
    }
}
