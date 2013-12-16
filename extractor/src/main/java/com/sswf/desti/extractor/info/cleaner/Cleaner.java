package com.sswf.desti.extractor.info.cleaner;

import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.jsoup.nodes.*;
import org.jsoup.parser.Tag;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

import com.google.common.collect.ImmutableSet;

/**
 * Taken and modified from the Jsoup's cleaner
 * 
 * @author Jsoup authors
 */
public class Cleaner {

    public static Document clean(Document dirty) {
        return new Cleaner().cleanInner(dirty);
    }

    public Document cleanInner(Document dirtyDocument) {
        Validate.notNull(dirtyDocument);
        if (dirtyDocument.body() == null) {
            return dirtyDocument;
        }

        Document clean = Document.createShell(dirtyDocument.baseUri());
        copySafeNodes(dirtyDocument.body(), clean.body());
        removeEmptyTags(clean);

        return clean;
    }

    private static void removeEmptyTags(Document doc) {
        // remove &nbsp; and the like, http://stackoverflow.com/a/7039867/861423
        doc.select(":containsOwn(\u00a0)").remove();

        // remove empty tags
        for (Element element : doc.select("*")) {
            boolean noText = !element.hasText();
            boolean notLinebreak = !element.tagName().equalsIgnoreCase("br");
            if (noText && notLinebreak) {
                if (element.parent() != null) {
                    element.remove();
                }
            }
        }

        // unwrap unneeded tags (that were used for formatting)
        doc.select("span,font,i,u,strong,b,center,nobr").unwrap();
    }

    private final class CleaningVisitor implements NodeVisitor {
        private Element destination; // current element to append nodes to

        private Set<String> tags = ImmutableSet.of("script", "link", "style");

        private CleaningVisitor(Element destination) {
            this.destination = destination;
        }

        @Override
        public void head(Node source, int depth) {
            if (destination == null) {
                return;
            }

            if (source instanceof Element) {
                Element sourceEl = (Element) source;
                if (!tagNeedsRemoving(sourceEl.tagName())) {
                    Element child = createSafeElement(sourceEl);
                    destination.appendChild(child);
                    destination = child;
                }
            } else if (source instanceof TextNode) {
                TextNode sourceText = (TextNode) source;
                TextNode destText = new TextNode(sourceText.getWholeText(), source.baseUri());
                destination.appendChild(destText);
            }
        }

        private boolean tagNeedsRemoving(String tagName) {
            return tags.contains(tagName);
        }

        @Override
        public void tail(Node source, int depth) {
            if (source instanceof Element && destination != null) {
                // would have descended, so pop destination stack
                destination = destination.parent();
            }
        }
    }

    private void copySafeNodes(Element source, Element dest) {
        CleaningVisitor cleaningVisitor = new CleaningVisitor(dest);
        NodeTraversor traversor = new NodeTraversor(cleaningVisitor);
        traversor.traverse(source);
    }

    private Element createSafeElement(Element sourceEl) {
        String sourceTag = sourceEl.tagName();
        Attributes destAttrs = new Attributes();
        Element dest = new Element(Tag.valueOf(sourceTag), sourceEl.baseUri(), destAttrs);

        Attributes sourceAttrs = sourceEl.attributes();
        for (Attribute sourceAttr : sourceAttrs) {
            if (needsKeeping(sourceAttr)) {
                destAttrs.put(sourceAttr);
            }
        }

        return dest;
    }

    private Set<String> attributes = ImmutableSet.of("href", "link");

    private boolean needsKeeping(Attribute sourceAttr) {
        String name = sourceAttr.getKey();
        return attributes.contains(name);
    }

}
