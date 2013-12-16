/**
 *
 */
package com.jdev.crawler.config.dom;

import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXSource;

import org.apache.commons.io.IOUtils;
import org.ccil.cowan.tagsoup.Parser;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.jdev.crawler.exception.SelectionException;

/**
 * @author Aleh
 */
public final class TagSoupDomNormaliser {

    /**
     * Hide constructor from public usage.
     */
    private TagSoupDomNormaliser() {
    }

    /**
     * Tagsoup normalization of HTML to the view acceptable by XPath.
     * 
     * @param source
     * @return
     * @throws SelectionException
     */
    public static Node convertToNormalisedNode(final String source) throws SelectionException {
        return convertToNormalisedNode(IOUtils.toInputStream(source));
    }

    /**
     * Tagsoup normalization of HTML to the view acceptable by XPath.
     * 
     * @param source
     * @return
     * @throws SelectionException
     */
    public static Node convertToNormalisedNode(final InputStream source) throws SelectionException {
        final Parser p = new Parser();
        try {
            p.setFeature(Parser.namespacePrefixesFeature, false);
            p.setFeature(Parser.resolveDTDURIsFeature, false);
            p.setFeature(Parser.ignoreBogonsFeature, true);
            p.setFeature(Parser.namespacesFeature, false);
            final Transformer transformer = TransformerFactory.newInstance().newTransformer();
            final DOMResult result = new DOMResult();
            final InputStreamReader isr = new InputStreamReader(source);
            transformer.transform(new SAXSource(p, new InputSource(isr)), result);
            return result.getNode();
        } catch (final SAXException e) {
            throw new SelectionException(e.getLocalizedMessage(), e);
        } catch (final TransformerException e1) {
            throw new SelectionException(e1.getLocalizedMessage(), e1);
        }
    }
}
