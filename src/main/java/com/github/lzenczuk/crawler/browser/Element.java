package com.github.lzenczuk.crawler.browser;

/**
 * @author lzenczuk 08/01/2016
 */
public interface Element {

    String getText();
    String getTag();
    void setValue(String value);
    void submit();
}
