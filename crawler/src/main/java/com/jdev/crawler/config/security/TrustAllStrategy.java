/**
 * 
 */
package com.jdev.crawler.config.security;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.conn.ssl.TrustStrategy;

/**
 * @author Aleh
 * 
 */
public class TrustAllStrategy implements TrustStrategy {

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.http.conn.ssl.TrustStrategy#isTrusted(java.security.cert.
     * X509Certificate[], java.lang.String)
     */
    @Override
    public boolean isTrusted(final X509Certificate[] chain, final String authType)
            throws CertificateException {
        return true;
    }
}