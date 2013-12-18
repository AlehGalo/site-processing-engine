/**
 * 
 */
package com.jdev.extractor.extractor.social;

/**
 * @author Aleh
 * 
 */
public class FacebookExtractor extends AbstractStartsWithExtractor {

    /**
     * 
     */
    public FacebookExtractor() {
	addStartsWithPattern("http://www.facebook.com");
	addStartsWithPattern("https://www.facebook.com");
	addStartsWithPattern("http://facebook.com");
	addStartsWithPattern("https://facebook.com");
    }

}
