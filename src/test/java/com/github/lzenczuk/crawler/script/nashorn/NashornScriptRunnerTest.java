package com.github.lzenczuk.crawler.script.nashorn;

import com.github.lzenczuk.crawler.browser.Browser;
import com.github.lzenczuk.crawler.browser.Page;
import com.github.lzenczuk.crawler.script.Result;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * @author lzenczuk 11/01/2016
 */
public class NashornScriptRunnerTest {

    @Test
    public void runScriptWithNoResult(){
        final NashornScriptRunner runner = new NashornScriptRunner();

        String script = "var x=10;";

        final Optional<Result> resultOptional = runner.run(script, null, null);

        assertThat(resultOptional, is(notNullValue()));
        assertThat(resultOptional.isPresent(), is(false));
    }

    @Test
    public void runScriptWithSuccessfulResult(){
        final NashornScriptRunner runner = new NashornScriptRunner();

        String script = "var x=10; ResultBuilder.success();";

        final Optional<Result> resultOptional = runner.run(script, null, null);

        assertThat(resultOptional, is(notNullValue()));
        assertThat(resultOptional.isPresent(), is(true));

        final Result result = resultOptional.get();

        assertThat(result, is(notNullValue()));
        assertThat(result.isSuccess(), is(true));
        assertThat(result.isError(), is(false));
        assertThat(result.getErrorMessage(), is(equalToIgnoringCase("")));
    }

    @Test
    public void runScriptWithErrorResult(){
        final NashornScriptRunner runner = new NashornScriptRunner();

        String script = "var x=10; ResultBuilder.error('error message');";

        final Optional<Result> resultOptional = runner.run(script, null, null);

        assertThat(resultOptional, is(notNullValue()));
        assertThat(resultOptional.isPresent(), is(true));

        final Result result = resultOptional.get();

        assertThat(result, is(notNullValue()));
        assertThat(result.isSuccess(), is(false));
        assertThat(result.isError(), is(true));
        assertThat(result.getErrorMessage(), is(equalToIgnoringCase("error message")));
    }

    @Test
    public void runScriptWithIncorrectResult(){
        final NashornScriptRunner runner = new NashornScriptRunner();

        String script = "var x=10; 23";

        final Optional<Result> resultOptional = runner.run(script, null, null);

        assertThat(resultOptional, is(notNullValue()));
        assertThat(resultOptional.isPresent(), is(true));

        final Result result = resultOptional.get();

        assertThat(result, is(notNullValue()));
        assertThat(result.isSuccess(), is(false));
        assertThat(result.isError(), is(true));
        assertThat(result.getErrorMessage(), is(containsString("Incorrect result type")));
    }

    @Test
    public void runScriptWithError(){
        final NashornScriptRunner runner = new NashornScriptRunner();

        String script = "var x=10; lkj";

        final Optional<Result> resultOptional = runner.run(script, null, null);

        assertThat(resultOptional, is(notNullValue()));
        assertThat(resultOptional.isPresent(), is(true));

        final Result result = resultOptional.get();

        assertThat(result, is(notNullValue()));
        assertThat(result.isSuccess(), is(false));
        assertThat(result.isError(), is(true));
        assertThat(result.getErrorMessage(), is(equalToIgnoringCase("Error: ReferenceError: \"lkj\" is not defined in <eval> at line number 1")));
    }

    @Test
    public void runSimpleScript(){
        final NashornScriptRunner runner = new NashornScriptRunner();

        final Map<String, Object> params = new HashMap<>();
        params.put("URL", "http://www.wikipedia.org");

        final Page page = mock(Page.class);

        final Browser browser = mock(Browser.class);
        when(browser.newPage()).thenReturn(page);

        String script = "var page = browser.newPage(); page.goToURL(URL); URL='new value'";

        runner.run(script, params, browser);

        verify(browser, only()).newPage();
        verify(page).goToURL("http://www.wikipedia.org");
        assertThat((String) params.get("URL"), is(equalToIgnoringCase("http://www.wikipedia.org")));
    }

}
