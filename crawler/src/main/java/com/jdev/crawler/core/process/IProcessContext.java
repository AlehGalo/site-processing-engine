package com.jdev.crawler.core.process;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;

import com.jdev.crawler.core.settings.ISettings;
import com.jdev.crawler.core.store.IFileStorage;
import com.jdev.crawler.core.user.IUserData;

public interface IProcessContext extends IRequestBuilderContext {

    /**
     * @return {@link CookieStore}
     */
    CookieStore getCookieStore();

    /**
     * @return {@link IFileStorage}
     */
    IFileStorage getFileStorage();

    /**
     * @return {@link ISettings}
     */
    ISettings getSettings();

    /**
     * @return {@link IUserData}
     */
    IUserData getUserData();

    /**
     * @return {@link HttpClient}
     */
    HttpClient getHttpClient();
}
