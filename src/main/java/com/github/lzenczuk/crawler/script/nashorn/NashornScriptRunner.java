package com.github.lzenczuk.crawler.script.nashorn;

import com.github.lzenczuk.crawler.browser.Browser;
import com.github.lzenczuk.crawler.script.Result;
import com.github.lzenczuk.crawler.script.ResultBuilder;
import com.github.lzenczuk.crawler.script.ScriptRunner;

import javax.script.*;
import java.util.Map;
import java.util.Optional;

/**
 * @author lzenczuk 11/01/2016
 */
public class NashornScriptRunner implements ScriptRunner {

    private final ScriptEngine engine;

    public NashornScriptRunner() {
        engine = new ScriptEngineManager().getEngineByName("nashorn");
    }

    public NashornScriptRunner(ScriptEngine engine) {
        this.engine = engine;
    }

    @Override
    public Optional<Result> run(String script, Map<String, Object> params, Browser browser) {

        SimpleBindings scriptBindings;

        if(params!=null){
            scriptBindings = new SimpleBindings(params);
        }else{
            scriptBindings = new SimpleBindings();
        }

        scriptBindings.put("browser", browser);
        scriptBindings.put("ResultBuilder", new ResultBuilder());

        try {
            final Object result = engine.eval(script, scriptBindings);

            if(result == null) return Optional.empty();

            if(result instanceof Result){
                return Optional.of((Result)result);
            }

            return Optional.of(Result.error("Incorrect result type: "+result));

        } catch (ScriptException e) {
            return Optional.of(Result.error("Error: "+e.getMessage()));
        }
    }
}
