package com.github.lzenczuk.crawler.browser;

import java.util.Optional;

/**
 * @author lzenczuk 08/01/2016
 */
public interface Page {
    void goToURL(String url);
    Optional<? extends Element> getElementById(String id);
    String getTitle();

    String getPageSource();
    void close();
}
