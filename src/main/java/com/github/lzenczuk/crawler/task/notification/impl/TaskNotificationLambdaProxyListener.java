package com.github.lzenczuk.crawler.task.notification.impl;

import com.github.lzenczuk.crawler.task.notification.TaskNotification;
import com.github.lzenczuk.crawler.task.notification.TaskNotificationListener;

import java.util.function.Consumer;

/**
 * @author lzenczuk 15/01/2016
 */
public class TaskNotificationLambdaProxyListener implements TaskNotificationListener {

    private final Consumer<TaskNotification> lambdaProxy;

    public TaskNotificationLambdaProxyListener(Consumer<TaskNotification> lambdaProxy) {
        this.lambdaProxy = lambdaProxy;
    }

    @Override
    public void update(TaskNotification taskNotification) {
        lambdaProxy.accept(taskNotification);
    }
}
