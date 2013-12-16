package com.jdev.crawler.core.process;

import com.jdev.crawler.exception.CrawlerException;

public interface IProcessResultHandler {
    
    void handle(IProcessSession session, byte[] content)
            throws CrawlerException;
}
