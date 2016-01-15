package com.github.lzenczuk.crawler.app.service;

import com.github.lzenczuk.crawler.task.Task;
import com.github.lzenczuk.crawler.task.TaskManager;
import com.github.lzenczuk.crawler.task.TaskStatus;
import com.github.lzenczuk.crawler.task.notification.TaskNotification;
import com.github.lzenczuk.crawler.task.notification.TaskNotificationStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * @author lzenczuk 12/01/2016
 */
@Service
public class TaskService {

    @Autowired
    private TaskManager taskQueue;

    @Autowired
    private TaskNotificationStorage taskNotificationStorage;

    public void addTaskToExecute(Task task){
        taskQueue.runTask(task);
    }

    public Set<Long> getAllTasksIds(){
        return taskNotificationStorage.getAllTaskIds();
    }

    public Optional<TaskStatus> getTaskStatus(long taskId){
        return taskNotificationStorage.getLatestNotification(taskId).map(TaskNotification::getStatus);
    }
}
