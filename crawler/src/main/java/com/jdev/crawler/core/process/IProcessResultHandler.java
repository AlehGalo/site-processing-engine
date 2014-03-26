package com.jdev.crawler.core.process;

import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.exception.CrawlerException;

public interface IProcessResultHandler {

    void handle(IProcessSession session, IEntity entity) throws CrawlerException;
}
