package com.github.lzenczuk.crawler.task;

import com.github.lzenczuk.crawler.browser.Browser;
import com.github.lzenczuk.crawler.script.ScriptRunner;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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

        final Thread thread = new Thread(() -> {
            while (true) {
                try {
                    final Task task = taskQueue.take();
                    runner.run(task.getScript(), task.getParams(), browser);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        thread.start();
    }

    public void addTask(Task task){
        taskQueue.add(task);
    }

    public int getWaitingTasks(){
        return taskQueue.size();
    }
}
