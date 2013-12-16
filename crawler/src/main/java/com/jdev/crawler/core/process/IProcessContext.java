package com.jdev.crawler.core.process;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;

import com.jdev.crawler.core.store.IFileStorage;
import com.jdev.crawler.core.user.IUserData;

public interface IProcessContext extends IRequestBuilderContext {

    HttpClient getClient();

    CookieStore getCookieStore();
    
    IFileStorage getFileStorage();

    int getRepeatTime();

    int getWaitInterval();

    boolean isStoreMarkup();

    IUserData getUserData();
}
