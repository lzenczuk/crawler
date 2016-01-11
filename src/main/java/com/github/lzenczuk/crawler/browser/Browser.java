package com.github.lzenczuk.crawler.browser;

/**
 * @author lzenczuk 08/01/2016
 */
public interface Browser {
    Page newPage();
    void stop();
}
