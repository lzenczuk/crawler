package com.github.lzenczuk.crawler.app;

import com.github.lzenczuk.crawler.browser.chrome.ChromeBrowser;
import com.github.lzenczuk.crawler.task.TaskManager;
import com.github.lzenczuk.crawler.task.notification.TaskNotificationListener;
import com.github.lzenczuk.crawler.task.notification.impl.TaskNotificationChainListener;
import com.github.lzenczuk.crawler.task.notification.impl.TaskNotificationInMemoryStorage;
import com.github.lzenczuk.crawler.task.notification.impl.TaskNotificationLambdaProxyListener;
import com.github.lzenczuk.crawler.task.script.nashorn.NashornTaskRunner;
import com.github.lzenczuk.crawler.task.MultiThreadTaskManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.IOException;

/**
 * @author lzenczuk 12/01/2016
 */
@Configuration
public class AppConfig {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Bean
    public TaskNotificationInMemoryStorage getTaskTaskNotificationStorage(){
        return new TaskNotificationInMemoryStorage();
    }

    @Bean
    public TaskManager getTaskManager(TaskNotificationListener taskNotificationListener) throws IOException {

        final TaskNotificationChainListener taskNotificationChainListener = new TaskNotificationChainListener();
        taskNotificationChainListener.addListener(taskNotificationListener);

        final TaskNotificationLambdaProxyListener wsTaskNotificationListener = new TaskNotificationLambdaProxyListener(taskNotification -> {
            simpMessagingTemplate.convertAndSend("/topic/notification", taskNotification);
        });
        taskNotificationChainListener.addListener(wsTaskNotificationListener);

        return new MultiThreadTaskManager(new NashornTaskRunner(new ChromeBrowser()), taskNotificationChainListener);
    }
}
