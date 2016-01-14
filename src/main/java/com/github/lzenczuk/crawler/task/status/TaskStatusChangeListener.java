package com.github.lzenczuk.crawler.task.status;

/**
 * @author lzenczuk 14/01/2016
 */
public interface TaskStatusChangeListener {
    void statusChange(long taskId, TaskStatus status);
}
