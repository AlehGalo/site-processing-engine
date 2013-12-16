/**
 * 
 */
package com.sswf.desti.extractor.social;

/**
 * @author Aleh
 * 
 */
public class TwitterExtractor extends AbstractStartsWithExtractor {

    /**
     * 
     */
    public TwitterExtractor() {
	addStartsWithPattern("http://twitter.com");
	addStartsWithPattern("https://twitter.com");
    }
}
