package com.github.lzenczuk.crawler.task;

import com.github.lzenczuk.crawler.browser.Browser;
import com.github.lzenczuk.crawler.script.Result;
import com.github.lzenczuk.crawler.script.ScriptRunner;

import java.util.Optional;
import java.util.concurrent.*;

/**
 * @author lzenczuk 12/01/2016
 */
public class TaskQueue {

    public static final int MAX_NUMBER_OF_WAITING_TASKS = 100;

    private BlockingQueue<Task> taskQueue = new ArrayBlockingQueue<>(MAX_NUMBER_OF_WAITING_TASKS);
    private final ScriptRunner runner;
    private final Browser browser;

    public TaskQueue(Browser browser, ScriptRunner runner) {
        this.browser = browser;
        this.runner = runner;

        final ExecutorService executorService = Executors.newFixedThreadPool(5);

        final Thread thread = new Thread(() -> {
            while (true) {
                try {

                    final Task task = taskQueue.take();

                    executorService.submit(() -> {
                        final Optional<Result> resultOptional = runner.run(task.getScript(), task.getParams(), browser);
                        System.out.println(resultOptional.map(result -> result.toString()).orElse("No result"));
                    });

                } catch (InterruptedException e) {
                    System.out.println("Exception: "+e.getMessage());
                    break;
                }
            }

            System.out.println("Task process ends");
        });

        thread.start();
    }

    public void addTask(Task task){
        taskQueue.add(task);
        System.out.println("Task added");
    }

    public int getWaitingTasks(){
        return taskQueue.size();
    }
}
