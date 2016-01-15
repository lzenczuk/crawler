package com.github.lzenczuk.crawler.task.notification;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author lzenczuk 15/01/2016
 */
public interface TaskNotificationStorage extends TaskNotificationListener {
    Set<Long> getAllTaskIds();
    Optional<TaskNotification> getLatestNotification(long taskId);
    List<TaskNotification> getNotificationHistory(long taskId);
}
