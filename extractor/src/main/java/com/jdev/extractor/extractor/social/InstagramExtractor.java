/**
 * 
 */
package com.jdev.extractor.extractor.social;

/**
 * @author Aleh
 * 
 */
public class InstagramExtractor extends AbstractStartsWithExtractor {

    /**
     * 
     */
    public InstagramExtractor() {
	addStartsWithPattern("http://instagram.com");
    }

}
