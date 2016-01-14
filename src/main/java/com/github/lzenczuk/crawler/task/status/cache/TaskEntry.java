package com.github.lzenczuk.crawler.task.status.cache;

import com.github.lzenczuk.crawler.task.status.TaskStatusChange;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lzenczuk 13/01/2016
 */
public class TaskEntry {

    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    private final long taskId;
    private LinkedList<TaskStatusChange> changeHistory = new LinkedList<>();

    public TaskEntry(long taskId) {
        this.taskId = taskId;
    }

    public void addStatusChange(TaskStatusChange change){
        rwl.writeLock().lock();
        changeHistory.add(change);
        rwl.writeLock().unlock();
    }

    public Optional<TaskStatusChange> getLatestChange(){
        TaskStatusChange last = null;

        rwl.readLock().lock();
        if(changeHistory.size()!=0){
            last = changeHistory.getLast();
        }
        rwl.readLock().unlock();

        return Optional.ofNullable(last);
    }

    public ArrayList<TaskStatusChange> getChangeHistory(){
        rwl.readLock().lock();
        final ArrayList<TaskStatusChange> taskStatusChanges = new ArrayList<>(changeHistory);
        rwl.readLock().unlock();

        return taskStatusChanges;
    }
}
