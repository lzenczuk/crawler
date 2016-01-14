package com.github.lzenczuk.crawler.task.script.nashorn;

import com.github.lzenczuk.crawler.browser.Browser;
import com.github.lzenczuk.crawler.task.Result;
import com.github.lzenczuk.crawler.task.script.ResultBuilder;
import com.github.lzenczuk.crawler.task.TaskRunner;
import com.github.lzenczuk.crawler.task.script.ScriptTask;

import javax.script.*;
import java.util.Optional;

/**
 * @author lzenczuk 11/01/2016
 */
public class NashornTaskRunner implements TaskRunner<ScriptTask> {

    private final ScriptEngine engine;
    private final Browser browser;

    public NashornTaskRunner(Browser browser) {
        this.browser = browser;
        this.engine = new ScriptEngineManager().getEngineByName("nashorn");
    }

    public NashornTaskRunner(Browser browser, ScriptEngine engine) {
        this.browser = browser;
        this.engine = engine;
    }

    @Override
    public Optional<Result> run(ScriptTask task) {
        System.out.println("Running script");

        SimpleBindings scriptBindings;

        if(task.getParams()!=null){
            scriptBindings = new SimpleBindings(task.getParams());
        }else{
            scriptBindings = new SimpleBindings();
        }

        scriptBindings.put("browser", browser);
        scriptBindings.put("ResultBuilder", new ResultBuilder());

        try {
            final Object result = engine.eval(task.getScript(), scriptBindings);

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
