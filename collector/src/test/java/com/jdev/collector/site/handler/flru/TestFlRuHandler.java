/**
 * 
 */
package com.jdev.collector.site.handler.flru;

import static com.google.common.base.Charsets.UTF_8;
import static com.jdev.crawler.core.selector.TestUtils.getFileContent;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.jdev.collector.site.handler.FlRuHandler;
import com.jdev.collector.site.handler.IObserver;
import com.jdev.crawler.core.process.IProcessSession;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.domain.domain.Article;

/**
 * @author Aleh
 * 
 */
@RunWith(Parameterized.class)
public class TestFlRuHandler {

    /**
     * @return Iterable resources.
     */
    @Parameters(name = "{index}: file name - {0}, result file name - {1}")
    public static Iterable<String[]> data() {
        return Arrays
                .<String[]> asList(new String[] { "input.html", "input.html", "windows-1251" });
    }

    /**
     * Simple
     */
    private static final URI URI_OBJECT = URI.create("http://abc.com/uri");

    /**
     * 
     */
    @Parameter
    public String fileName;

    /**
     * 
     */
    @Parameter(value = 1)
    public String resultFileName;

    /**
     * 
     */
    @Parameter(value = 2)
    public String charsetName;

    @Test
    public void testContentSelection() {
        IEntity entity = mock(IEntity.class);
        FlRuHandler handler = new FlRuHandler();
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
