package com.github.lzenczuk.crawler.app;

import com.github.lzenczuk.crawler.browser.Browser;
import com.github.lzenczuk.crawler.browser.chrome.ChromeBrowser;
import com.github.lzenczuk.crawler.script.ScriptRunner;
import com.github.lzenczuk.crawler.script.nashorn.NashornScriptRunner;
import com.github.lzenczuk.crawler.task.TaskQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author lzenczuk 12/01/2016
 */
@Configuration
public class AppConfig {

    @Bean
    public Browser getChromeBrowser() throws IOException {
        return new ChromeBrowser();
    }

    @Bean
    public ScriptRunner getNashornScriptRunner(){
        return new NashornScriptRunner();
    }

    @Bean
    public TaskQueue getTaskQueue() throws IOException {
        return new TaskQueue(getChromeBrowser(), getNashornScriptRunner());
    }
}
