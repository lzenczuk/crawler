package com.github.lzenczuk.crawler.browser.phantomjs;

import com.github.lzenczuk.crawler.browser.Page;

import java.io.File;
import java.io.IOException;

import com.github.lzenczuk.crawler.browser.selenium.SeleniumPage;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author lzenczuk 08/01/2016
 */
public class PhjsBrowserTest {

    @Test
    public void createBrowserUsingDefaultPath() throws IOException {
        final PhjsBrowser phjsBrowser = new PhjsBrowser();
        assertThat(phjsBrowser, is(notNullValue()));
        phjsBrowser.stop();
    }

    @Test(expected = IllegalArgumentException.class)
    public void createBrowserUsingNullPath() throws IOException {
        final PhjsBrowser phjsBrowser = new PhjsBrowser(null);
        phjsBrowser.stop();
    }

    @Test(expected = IllegalArgumentException.class)
    public void createBrowserUsingIncorrectPath() throws IOException {
        final PhjsBrowser phjsBrowser = new PhjsBrowser(new File("/bin/ls").toURI().toURL());
        phjsBrowser.stop();
    }

    @Test
    public void getPage() throws IOException {
        final PhjsBrowser phjsBrowser = new PhjsBrowser();
        assertThat(phjsBrowser, is(notNullValue()));

        final Page page = phjsBrowser.newPage();
        assertThat(page, is(notNullValue()));
        assertThat(page, is(instanceOf(SeleniumPage.class)));

        phjsBrowser.stop();
    }
}
