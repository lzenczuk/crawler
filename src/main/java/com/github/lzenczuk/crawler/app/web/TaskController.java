package com.github.lzenczuk.crawler.app.web;

import com.github.lzenczuk.crawler.app.service.TaskService;
import com.github.lzenczuk.crawler.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzenczuk 12/01/2016
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(method = RequestMethod.POST)
    public String addTask(String script){

        System.out.println("Script: "+script);
        taskService.addTaskToExecute(new Task(null, script));

        return "hello";
    }
}
