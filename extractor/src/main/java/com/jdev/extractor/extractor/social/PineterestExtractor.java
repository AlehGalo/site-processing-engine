/**
 * 
 */
package com.jdev.extractor.extractor.social;

/**
 * @author Aleh
 * 
 */
public class PineterestExtractor extends AbstractStartsWithExtractor {

    /**
     * 
     */
    public PineterestExtractor() {
	addStartsWithPattern("http://pinterest.com");
    }

}
