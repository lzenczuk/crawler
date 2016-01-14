package com.github.lzenczuk.crawler.task.status.cache;

import com.github.lzenczuk.crawler.task.status.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lzenczuk 13/01/2016
 */
public class TaskStatusCache implements TaskStatusChangeListener, TaskStatusStorage {

    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private Map<Long, TaskEntry> taskEntryMap = new HashMap<>();

    @Override
    public void statusChange(long taskId, TaskStatus status){
        rwl.writeLock().lock();
        if(!taskEntryMap.containsKey(taskId)){
            taskEntryMap.put(taskId, new TaskEntry(taskId));
        }
        rwl.writeLock().unlock();

        rwl.readLock().lock();
        final TaskEntry taskEntry = taskEntryMap.get(taskId);
        rwl.readLock().unlock();

        if(taskEntry!=null){
            taskEntry.addStatusChange(new TaskStatusChange(taskId, status, new Date()));
        }
    }

    @Override
    public Optional<TaskStatus> getCurrentStatus(long taskId){
        rwl.readLock().lock();
        final Optional<TaskEntry> taskEntryOptional = Optional.ofNullable(taskEntryMap.get(taskId));
        rwl.readLock().unlock();

        return taskEntryOptional.flatMap(taskEntry -> taskEntry.getLatestChange().map(TaskStatusChange::getStatus));
    }

    @Override
    public List<TaskStatusChange> getStatusChangeHistory(long taskId){
        rwl.readLock().lock();
        final Optional<TaskEntry> taskEntryOptional = Optional.ofNullable(taskEntryMap.get(taskId));
        rwl.readLock().unlock();

        return taskEntryOptional.map(taskEntry -> taskEntry.getChangeHistory()).orElse(new ArrayList<>());
    }

    @Override
    public Set<Long> getAllTasksIds() {
        rwl.readLock().lock();
        final Set<Long> ids = taskEntryMap.keySet();
        rwl.readLock().unlock();

        return ids;
    }
}
