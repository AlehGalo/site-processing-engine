/**
 * 
 */
package com.jdev.collector.site.handler;

import static com.google.common.base.Charsets.UTF_8;
import static com.jdev.crawler.core.selector.TestUtils.getFileContent;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.jdev.crawler.core.process.IProcessSession;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.domain.entity.Article;

/**
 * @author Aleh
 * 
 */
@RunWith(Parameterized.class)
public abstract class TestAbstractHandler {

    /**
     * Simple
     */
    private static final URI URI_OBJECT = URI.create("http://abc.com/uri");

    /**
     * 
     */
    public String fileName;

    /**
     * 
     */
    public String resultFileName;

    /**
     * 
     */
    public String charsetName;

    /**
     * @param fileName
     * @param resultFileName
     * @param charsetName
     */
    public TestAbstractHandler(final String fileName, final String resultFileName,
            final String charsetName) {
        this.fileName = fileName;
        this.resultFileName = resultFileName;
        this.charsetName = charsetName;
    }

    /**
     * 
     */
    protected final void testHandler() {
        IEntity entity = mock(IEntity.class);
        ArticleWatcher handler = createHandler();
        Charset charset = getCharset();
        when(entity.getCharset()).thenReturn(charset);
        when(entity.getContent()).thenReturn(getFileContent(fileName, this, charset).getBytes());
        when(entity.getEntityUri()).thenReturn(URI_OBJECT);
        ActionObserver observer = new ActionObserver();
        handler.addListener(observer);
        try {
            handler.handle(mock(IProcessSession.class), entity);
        } catch (CrawlerException e) {
            Assert.fail("Content selection exception");
        }
        Article article = observer.article;
        Assert.assertTrue(isNotBlank(article.getContent()));
        Assert.assertTrue(isNotBlank(article.getTitle()));
    }

    /**
     * @return
     */
    protected abstract ArticleWatcher createHandler();

    /**
     * @return charset.
     */
    private Charset getCharset() {
        return isEmpty(charsetName) ? UTF_8 : Charset.forName(charsetName);
    }

    /**
     * @author Aleh
     * 
     */
    private class ActionObserver implements IObserver {

        /**
         * Article var.
         */
        private Article article;

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.jdev.collector.site.handler.IObserver#articleCollected(com.jdev
         * .domain.domain.Article)
         */
        @Override
        public void articleCollected(final Article article) {
            this.article = article;
        }
    }

}
