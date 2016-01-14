package com.github.lzenczuk.crawler.app;

import com.github.lzenczuk.crawler.browser.Browser;
import com.github.lzenczuk.crawler.browser.chrome.ChromeBrowser;
import com.github.lzenczuk.crawler.task.TaskManager;
import com.github.lzenczuk.crawler.task.TaskRunner;
import com.github.lzenczuk.crawler.task.script.nashorn.NashornTaskRunner;
import com.github.lzenczuk.crawler.task.MultiThreadTaskManager;
import com.github.lzenczuk.crawler.task.status.TaskStatusChangeListener;
import com.github.lzenczuk.crawler.task.status.cache.TaskStatusCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author lzenczuk 12/01/2016
 */
@Configuration
public class AppConfig {

    @Bean
    public TaskStatusCache getTaskStatusChangeCache(){
        return new TaskStatusCache();
    }

    @Bean
    public TaskManager getTaskManager(TaskStatusChangeListener taskStatusChangeListener) throws IOException {
        return new MultiThreadTaskManager(new NashornTaskRunner(new ChromeBrowser()), taskStatusChangeListener);
    }
}
