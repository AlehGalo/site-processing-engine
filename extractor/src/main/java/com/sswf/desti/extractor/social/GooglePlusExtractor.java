/**
 * 
 */
package com.sswf.desti.extractor.social;


/**
 * @author Aleh
 * 
 */
public class GooglePlusExtractor extends AbstractStartsWithExtractor {

    /**
     * 
     */
    public GooglePlusExtractor() {
	addStartsWithPattern("https://plus.google.com");
    }

}
