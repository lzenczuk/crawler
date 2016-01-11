package com.github.lzenczuk.crawler.browser.selenium;

import com.github.lzenczuk.crawler.browser.Element;
import com.github.lzenczuk.crawler.browser.Page;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Optional;

/**
 * @author lzenczuk 08/01/2016
 */
public class SeleniumPage implements Page {

    private final RemoteWebDriver driver;

    public SeleniumPage(RemoteWebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void goToURL(String url) {
        driver.get(url);
    }

    @Override
    public Optional<? extends Element> getElementById(String id) {
        try {
            return Optional.of(new SeleniumElement(driver.findElementById(id)));
        }catch (NoSuchElementException ex){
            return Optional.empty();
        }
    }

    @Override
    public String getTitle() {
        return driver.getTitle();
    }

    @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

    @Override
    public void close() {
        driver.close();
    }
}
