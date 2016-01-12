package com.github.lzenczuk.crawler.task;

import java.util.Map;

/**
 * @author lzenczuk 12/01/2016
 */
public class Task {
    private final Map<String, Object> params;
    private final String script;

    public Task(Map<String, Object> params, String script) {
        this.params = params;
        this.script = script;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public String getScript() {
        return script;
    }
}
