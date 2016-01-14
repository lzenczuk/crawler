package com.github.lzenczuk.crawler.app.web;

import com.github.lzenczuk.crawler.app.service.TaskService;
import com.github.lzenczuk.crawler.app.service.TasksIdGenerator;
import com.github.lzenczuk.crawler.task.script.ScriptTask;
import com.github.lzenczuk.crawler.task.status.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

/**
 * @author lzenczuk 12/01/2016
 */
@RestController
@RequestMapping("/task")
public class ScriptTaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TasksIdGenerator tasksIdGenerator;

    @RequestMapping(method = RequestMethod.GET)
    public Set<Long> getTasksIds(){
        return taskService.getAllTasksIds();
    }

    @RequestMapping(method = RequestMethod.POST)
    public long createTask(String script){

        System.out.println("Script: "+script);
        final long taskId = tasksIdGenerator.generateId();

        taskService.addTaskToExecute(new ScriptTask(taskId, script, null));

        return taskId;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<TaskStatus> getTasksStatus(@PathVariable(value = "id") long taskId){
        return taskService.getTaskStatus(taskId);
    }
}
