package com.github.lzenczuk.crawler.browser.selenium;

import com.github.lzenczuk.crawler.browser.Element;
import com.github.lzenczuk.crawler.browser.Page;
import com.github.lzenczuk.crawler.browser.phantomjs.PhjsBrowser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author lzenczuk 08/01/2016
 */
public class SeleniumPageTest {

    private PhjsBrowser phjsBrowser;
    private Page page;

    @Before
    public void init() throws IOException {
        phjsBrowser = new PhjsBrowser();
        page = phjsBrowser.newPage();
    }

    @After
    public void clean(){
        phjsBrowser.stop();
    }

    @Test
    public void getElementFromNotLoadedPage() throws IOException {

        final Optional<? extends Element> elementOptional = page.getElementById("test");

        assertThat(elementOptional, is(notNullValue()));
        assertThat(elementOptional.isPresent(), is(false));
    }

    @Test
    public void getTitleOfNotLoadedPage() throws IOException {

        assertThat(page.getTitle().length(), is(0));
    }

    @Test
    public void getSourceOfNotLoadedPage() throws IOException {

        assertThat(page.getPageSource(), is(equalToIgnoringCase("<html><head></head><body></body></html>")));
    }

    @Test
    public void getSourceOfWikipediaOrg() throws IOException {
        page.goToURL("https://www.wikipedia.org/");
        assertThat(page.getPageSource(), is(not(equalToIgnoringCase("<html><head></head><body></body></html>"))));
    }

    @Test
    public void getTitleOfWikipediaOrg() throws IOException {
        page.goToURL("https://www.wikipedia.org/");
        assertThat(page.getTitle(), is("Wikipedia"));
    }

    @Test
    public void getSearchInputOfWikipediaOrg() throws IOException {
        page.goToURL("https://www.wikipedia.org/");
        final Optional<? extends Element> searchInput = page.getElementById("searchInput");

        assertThat(searchInput, is(notNullValue()));
        assertThat(searchInput.isPresent(), is(true));
    }

    @Test
    public void submitQueryOnWikipediaOrg() throws IOException {
        page.goToURL("https://www.wikipedia.org/");
        page.getElementById("searchInput").ifPresent(el -> {
            el.setValue("phantomjs");
            el.submit();
        });

        assertThat(page.getTitle(), is(startsWith("PhantomJS")));
    }
}
