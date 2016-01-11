package com.github.lzenczuk.crawler.browser.selenium;

import com.github.lzenczuk.crawler.browser.Element;
import org.openqa.selenium.WebElement;

/**
 * @author lzenczuk 08/01/2016
 */
public class SeleniumElement implements Element {

    private final WebElement element;

    public SeleniumElement(WebElement element) {
        this.element = element;
    }

    @Override
    public String getText() {
        return element.getText();
    }

    @Override
    public String getTag() {
        return this.element.getTagName();
    }

    @Override
    public void setValue(String value) {
        this.element.sendKeys(value);
    }

    @Override
    public void submit() {
        this.element.submit();
    }
}
