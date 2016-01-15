package com.github.lzenczuk.crawler.task.notification.impl;

import com.github.lzenczuk.crawler.task.notification.TaskNotification;
import com.github.lzenczuk.crawler.task.notification.TaskNotificationStorage;
import com.github.lzenczuk.crawler.task.notification.impl.inmemory.TaskNotifications;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lzenczuk 15/01/2016
 */
public class TaskNotificationInMemoryStorage implements TaskNotificationStorage {

    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private Map<Long, TaskNotifications> taskNotificationsMap = new HashMap<>();

    @Override
    public Set<Long> getAllTaskIds() {
        rwl.readLock().lock();
        final Set<Long> ids = taskNotificationsMap.keySet();
        rwl.readLock().unlock();

        return ids;
    }

    @Override
    public Optional<TaskNotification> getLatestNotification(long taskId) {
        rwl.readLock().lock();
        final Optional<TaskNotifications> taskNotificationsOptional = Optional.ofNullable(taskNotificationsMap.get(taskId));
        rwl.readLock().unlock();

        return taskNotificationsOptional.flatMap(TaskNotifications::getLast);
    }

    @Override
    public List<TaskNotification> getNotificationHistory(long taskId) {
        rwl.readLock().lock();
        final Optional<TaskNotifications> taskNotificationsOptional = Optional.ofNullable(taskNotificationsMap.get(taskId));
        rwl.readLock().unlock();

        return taskNotificationsOptional.map(TaskNotifications::getAll).orElse(new ArrayList<>());
    }

    @Override
    public void update(TaskNotification taskNotification) {
        final long taskId = taskNotification.getTaskId();

        rwl.writeLock().lock();
        if(!taskNotificationsMap.containsKey(taskId)){
            taskNotificationsMap.put(taskId, new TaskNotifications(taskId));
        }
        rwl.writeLock().unlock();

        rwl.readLock().lock();
        final TaskNotifications taskNotifications = taskNotificationsMap.get(taskId);
        rwl.readLock().unlock();

        if(taskNotifications!=null){
            taskNotifications.add(taskNotification);
        }
    }
}
