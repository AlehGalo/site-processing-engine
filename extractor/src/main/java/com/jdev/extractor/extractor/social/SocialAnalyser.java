/**
 * 
 */
package com.jdev.extractor.extractor.social;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.domain.Information;
import com.jdev.domain.adapter.IContent;
import com.jdev.domain.social.Social;
import com.jdev.extractor.analyser.AbstractAnalyser;
import com.jdev.extractor.extractor.IExtractor;

/**
 * @author Aleh
 * 
 */
public class SocialAnalyser extends AbstractAnalyser {

    /**
     * Logger.
     */
    private static Logger LOGGER = LoggerFactory.getLogger(SocialAnalyser.class);

    /**
     * 
     */
    public SocialAnalyser() {
        addExtractor(new TwitterExtractor());
        addExtractor(new GooglePlusExtractor());
        addExtractor(new InstagramExtractor());
        addExtractor(new FacebookExtractor());
        addExtractor(new PineterestExtractor());
    }

    @Override
    public void analyse(final IContent url, final Information i) {
        if (url != null && i != null) {
            final Social social = (Social) i.getSocial();
            for (final IExtractor<Set<String>, IContent> element : setOfExtractors) {
                final Set<String> set = element.extract(url);
                if (!set.isEmpty()) {
                    final String urlProcess = (String) CollectionUtils.get(set, 0);
                    try {
                        final URI uriToSave = new URI(urlProcess);
                        if (element instanceof TwitterExtractor) {
                            social.setTwitterUri(uriToSave);
                        } else if (element instanceof PineterestExtractor) {
                            social.setPineterestUri(uriToSave);
                        } else if (element instanceof InstagramExtractor) {
                            social.setInstagramUri(uriToSave);
                        } else if (element instanceof GooglePlusExtractor) {
                            social.setGooglePlusUri(uriToSave);
                        } else if (element instanceof FacebookExtractor) {
                            social.setFacebookUri(uriToSave);
                        }
                    } catch (final URISyntaxException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

}
