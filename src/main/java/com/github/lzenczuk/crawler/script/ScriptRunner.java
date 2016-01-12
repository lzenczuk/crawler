package com.github.lzenczuk.crawler.script;

import com.github.lzenczuk.crawler.browser.Browser;

import java.util.Map;
import java.util.Optional;

/**
 * @author lzenczuk 11/01/2016
 */
public interface ScriptRunner {
    Optional<Result> run(String script, Map<String, Object> params, Browser browser);
}
