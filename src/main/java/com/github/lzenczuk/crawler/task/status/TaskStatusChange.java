package com.github.lzenczuk.crawler.task.status;

import java.util.Date;

/**
 * @author lzenczuk 13/01/2016
 */
public class TaskStatusChange {
    private final long id;
    private final TaskStatus status;
    private final Date time;

    public TaskStatusChange(long id, TaskStatus status, Date time) {
        this.id = id;
        this.status = status;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "TaskStatusChange{" +
                "id=" + id +
                ", status=" + status +
                ", time=" + time +
                '}';
    }
}
