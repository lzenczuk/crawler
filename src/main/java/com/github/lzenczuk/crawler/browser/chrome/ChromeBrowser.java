package com.github.lzenczuk.crawler.browser.chrome;

import com.github.lzenczuk.crawler.browser.Browser;
import com.github.lzenczuk.crawler.browser.Page;
import com.github.lzenczuk.crawler.browser.selenium.SeleniumPage;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author lzenczuk 08/01/2016
 */
public class ChromeBrowser implements Browser {

    public static final String CHROME_DRIVER_LINUX_PATH = "/chrome_driver/linux/chromedriver";

    private ChromeDriverService service;

    public ChromeBrowser() throws IOException {
        this(ChromeBrowser.class.getResource(CHROME_DRIVER_LINUX_PATH));
    }

    public ChromeBrowser(URL chromeExecutable) throws IOException {

        if(chromeExecutable==null){
            throw new IllegalArgumentException("Incorrect chrome executable path");
        }

        try {
            service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(FileUtils.toFile(chromeExecutable))
                    .usingAnyFreePort()
                    .build();

            service.start();
        }catch (WebDriverException ex){
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public Page newPage() {
        return new SeleniumPage(new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome()));
    }

    @Override
    public void stop() {
        if(service!=null && service.isRunning()){
            service.stop();
        }
    }
}
