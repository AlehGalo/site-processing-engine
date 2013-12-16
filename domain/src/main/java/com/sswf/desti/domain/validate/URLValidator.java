/**
 * 
 */
package com.sswf.desti.domain.validate;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Aleh
 * 
 */
public class URLValidator implements IValidate {

    final String url;

    public URLValidator(final String url) {
        this.url = url;
    }

    @Override
    public boolean validate() {
        return isValid(url);
    }

    public static boolean isValid(String url) {
        try {
            if (new URL(url).getHost() == null) {
                return false;
            }
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }

}
