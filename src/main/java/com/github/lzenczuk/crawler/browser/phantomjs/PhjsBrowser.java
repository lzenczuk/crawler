package com.github.lzenczuk.crawler.browser.phantomjs;

import com.github.lzenczuk.crawler.browser.Browser;
import com.github.lzenczuk.crawler.browser.Page;
import com.github.lzenczuk.crawler.browser.selenium.SeleniumPage;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URL;

/**
 * @author lzenczuk 08/01/2016
 */
public class PhjsBrowser implements Browser {

    public static final String PHANTOMJS_LINUX_PATH = "/phantomjs/linux/phantomjs";

    private PhantomJSDriverService service;

    public PhjsBrowser() throws IOException {
        this(PhjsBrowser.class.getResource(PHANTOMJS_LINUX_PATH));
    }

    protected PhjsBrowser(URL phantomjsExecutable) throws IOException {

        if(phantomjsExecutable==null){
            throw new IllegalArgumentException("Incorrect phantomjs executable path");
        }

        try {
            service = new PhantomJSDriverService.Builder()
                    .usingPhantomJSExecutable(FileUtils.toFile(phantomjsExecutable))
                    .usingAnyFreePort()
                    .build();

            service.start();
        }catch (WebDriverException ex){
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public Page newPage() {
        return new SeleniumPage(new RemoteWebDriver(service.getUrl(), DesiredCapabilities.phantomjs()));
    }

    @Override
    public void stop() {
        if(service!=null && service.isRunning()){
            service.stop();
        }
    }
}
