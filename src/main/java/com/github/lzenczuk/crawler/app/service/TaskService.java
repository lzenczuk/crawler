package com.github.lzenczuk.crawler.app.service;

import com.github.lzenczuk.crawler.task.Task;
import com.github.lzenczuk.crawler.task.TaskManager;
import com.github.lzenczuk.crawler.task.status.TaskStatus;
import com.github.lzenczuk.crawler.task.status.TaskStatusStorage;
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
    private TaskStatusStorage taskStatusStorage;

    public void addTaskToExecute(Task task){
        taskQueue.runTask(task);
    }

    public Set<Long> getAllTasksIds(){
        return taskStatusStorage.getAllTasksIds();
    }

    public Optional<TaskStatus> getTaskStatus(long taskId){
        return taskStatusStorage.getCurrentStatus(taskId);
    }
}
