package com.github.lzenczuk.crawler.task.script;

import com.github.lzenczuk.crawler.task.Task;

import java.util.Map;

/**
 * @author lzenczuk 12/01/2016
 */
public class ScriptTask implements Task {
    private final long id;
    private final Map<String, Object> params;
    private final String script;

    public ScriptTask(long id, String script, Map<String, Object> params) {
        this.id = id;
        this.params = params;
        this.script = script;
    }

    @Override
    public long getId() {
        return id;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public String getScript() {
        return script;
    }
}
