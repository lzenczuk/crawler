package com.github.lzenczuk.crawler.task.notification.impl.inmemory;

import com.github.lzenczuk.crawler.task.notification.TaskNotification;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lzenczuk 15/01/2016
 */
public class TaskNotifications {

    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    private final long taskId;
    private LinkedList<TaskNotification> history = new LinkedList<>();

    public TaskNotifications(long taskId) {
        this.taskId = taskId;
    }

    public void add(TaskNotification notification){
        rwl.writeLock().lock();
        history.add(notification);
        rwl.writeLock().unlock();
    }

    public Optional<TaskNotification> getLast(){
        TaskNotification last = null;

        rwl.readLock().lock();
        if(history.size()!=0){
            last = history.getLast();
        }
        rwl.readLock().unlock();

        return Optional.ofNullable(last);
    }

    public ArrayList<TaskNotification> getAll(){
        rwl.readLock().lock();
        final ArrayList<TaskNotification> all = new ArrayList<>(history);
        rwl.readLock().unlock();

        return all;
    }
}
