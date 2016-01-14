package com.github.lzenczuk.crawler.task.status;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author lzenczuk 14/01/2016
 */
public interface TaskStatusStorage {
    Optional<TaskStatus> getCurrentStatus(long taskId);

    List<TaskStatusChange> getStatusChangeHistory(long taskId);

    Set<Long> getAllTasksIds();
}
