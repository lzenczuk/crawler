package com.github.lzenczuk.crawler.task.notification;

import com.github.lzenczuk.crawler.task.TaskStatus;

import java.util.Date;

/**
 * @author lzenczuk 15/01/2016
 */
public class TaskNotification {
    private final long taskId;
    private final Date date;
    private final TaskStatus status;
    private final String source;
    private final String message;

    public TaskNotification(long taskId, Date date, TaskStatus status, String source, String message) {
        this.taskId = taskId;
        this.date = date;
        this.status = status;
        this.source = source;
        this.message = message;
    }

    public long getTaskId() {
        return taskId;
    }

    public Date getDate() {
        return date;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getSource() {
        return source;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "TaskNotification{" +
                "taskId=" + taskId +
                ", date=" + date +
                ", status=" + status +
                ", source='" + source + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
