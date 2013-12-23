/**
 *
 */
package com.jdev.crawler.core;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import org.apache.http.Consts;
import org.apache.http.HttpVersion;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.AutoRetryHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultServiceUnavailableRetryStrategy;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;

import com.jdev.crawler.config.security.TrustAllStrategy;

/**
 * @author Aleh
 */
public final class HttpClientFactory {

    /**
     *
     */
    private HttpClientFactory() {
    }

    /**
     * Store.
     */
    private static CookieStore cookieStore;

    /**
     * @return HttpClient configured.
     */
    public static HttpClient createHttpClient(AgentEnum agent) {
        try {
            final DefaultHttpClient client = new DefaultHttpClient(buildClientConnectionManager());
            client.setParams(buildParameters(agent));
            client.setRedirectStrategy(new LaxRedirectStrategy());
            client.setHttpRequestRetryHandler(new StandardHttpRequestRetryHandler());
            client.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
            cookieStore = client.getCookieStore();
            return new AutoRetryHttpClient(client, new DefaultServiceUnavailableRetryStrategy(5,
                    5000));
        } catch (final Exception e) {
            throw new RuntimeException("HttpClient is not created.", e);
        }
    }

    /**
     * @return
     */
    private static HttpParams buildParameters(AgentEnum agent) {
        final HttpParams params = new SyncBasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, Consts.UTF_8.toString());
        HttpProtocolParams.setUserAgent(params, agent.getAgentText());
        params.setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        params.setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
        params.setParameter(ClientPNames.HANDLE_REDIRECTS, true);
        params.setParameter(ClientPNames.CONN_MANAGER_TIMEOUT, 5000L);
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        return params;
    }

    /**
     * @return
     * @throws KeyManagementException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     */
    private static SchemeRegistry buildSchemeRegistry() throws KeyManagementException,
            UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        final Scheme httpScheme = new Scheme("http", 80, PlainSocketFactory.getSocketFactory());
        final Scheme httpsScheme = new Scheme("https", 443, new SSLSocketFactory(
                new TrustAllStrategy(), SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER));
        final SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(httpsScheme);
        schemeRegistry.register(httpScheme);
        return schemeRegistry;
    }

    /**
     * @return
     * @throws KeyManagementException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     */
    private static ClientConnectionManager buildClientConnectionManager()
            throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException,
            KeyStoreException {
        return new PoolingClientConnectionManager(buildSchemeRegistry());
    }

    /**
     * @return the cookieStore
     */
    public static CookieStore getCookieStore() {
        return cookieStore;
    }
}
