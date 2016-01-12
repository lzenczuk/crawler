package com.github.lzenczuk.crawler.app.service;

import com.github.lzenczuk.crawler.task.Task;
import com.github.lzenczuk.crawler.task.TaskQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lzenczuk 12/01/2016
 */
@Service
public class TaskService {

    @Autowired
    private TaskQueue taskQueue;

    public void addTaskToExecute(Task task){
        taskQueue.addTask(task);
    }
}
