package com.github.lzenczuk.crawler.app;

import com.github.lzenczuk.crawler.browser.chrome.ChromeBrowser;
import com.github.lzenczuk.crawler.task.TaskManager;
import com.github.lzenczuk.crawler.task.notification.TaskNotificationListener;
import com.github.lzenczuk.crawler.task.notification.impl.TaskNotificationInMemoryStorage;
import com.github.lzenczuk.crawler.task.script.nashorn.NashornTaskRunner;
import com.github.lzenczuk.crawler.task.MultiThreadTaskManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author lzenczuk 12/01/2016
 */
@Configuration
public class AppConfig {

    @Bean
    public TaskNotificationInMemoryStorage getTaskTaskNotificationStorage(){
        return new TaskNotificationInMemoryStorage();
    }

    @Bean
    public TaskManager getTaskManager(TaskNotificationListener taskNotificationListener) throws IOException {
        return new MultiThreadTaskManager(new NashornTaskRunner(new ChromeBrowser()), taskNotificationListener);
    }
}
