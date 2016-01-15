package com.github.lzenczuk.crawler.task.notification.impl;

import com.github.lzenczuk.crawler.task.notification.TaskNotification;
import com.github.lzenczuk.crawler.task.notification.TaskNotificationListener;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lzenczuk 15/01/2016
 */
public class TaskNotificationChainListener implements TaskNotificationListener {

    private final List<TaskNotificationListener> listeners;

    public TaskNotificationChainListener() {
        this.listeners = new LinkedList<>();
    }

    public TaskNotificationChainListener(List<TaskNotificationListener> listeners) {
        this.listeners = listeners;
    }

    public void addListener(TaskNotificationListener listener){
        listeners.add(listener);
    }

    @Override
    public void update(TaskNotification taskNotification) {
        listeners.parallelStream().forEach(listener -> listener.update(taskNotification));
    }
}
