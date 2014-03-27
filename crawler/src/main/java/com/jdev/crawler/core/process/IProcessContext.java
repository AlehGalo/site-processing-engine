package com.jdev.crawler.core.process;

import org.apache.http.client.CookieStore;

import com.jdev.crawler.core.settings.ISettings;
import com.jdev.crawler.core.store.IFileStorage;
import com.jdev.crawler.core.user.IUserData;

public interface IProcessContext extends IRequestBuilderContext {

    CookieStore getCookieStore();

    IFileStorage getFileStorage();

    ISettings getSettings();

    IUserData getUserData();
}
