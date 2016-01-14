package com.github.lzenczuk.crawler.task;

import com.github.lzenczuk.crawler.task.status.TaskStatus;
import com.github.lzenczuk.crawler.task.status.TaskStatusChangeListener;

import java.util.Optional;
import java.util.concurrent.*;

/**
 * @author lzenczuk 12/01/2016
 */
public class MultiThreadTaskManager<T extends Task> implements TaskManager<T> {

    public static final int MAX_NUMBER_OF_WAITING_TASKS = 100;
    public static final int DEFAULT_NUMBER_OF_THREADS = 5;

    private final BlockingQueue<T> taskQueue = new ArrayBlockingQueue<>(MAX_NUMBER_OF_WAITING_TASKS);

    private final TaskRunner runner;
    private final int threads;
    private final TaskStatusChangeListener taskStatusChangeListener;

    public MultiThreadTaskManager(TaskRunner runner, TaskStatusChangeListener taskStatusChangeListener) {
        this(DEFAULT_NUMBER_OF_THREADS, runner, taskStatusChangeListener);
    }

    public MultiThreadTaskManager(int threads, TaskRunner runner, TaskStatusChangeListener taskStatusChangeListener) {
        this.runner = runner;
        this.taskStatusChangeListener = taskStatusChangeListener;
        this.threads = threads;

        init();
    }

    private void init() {
        final ExecutorService executorService = Executors.newFixedThreadPool(threads);

        final Thread thread = new Thread(() -> {
            while (true) {
                try {

                    final T task = taskQueue.take();

                    executorService.submit(() -> {
                        taskStatusChangeListener.statusChange(task.getId(), TaskStatus.RUNNING);
                        final Optional<Result> resultOptional = runner.run(task);

                        resultOptional.ifPresent(result -> {
                            if(result.isSuccess()){
                                taskStatusChangeListener.statusChange(task.getId(), TaskStatus.FINISHED_SUCCESFULY);
                            }else{
                                taskStatusChangeListener.statusChange(task.getId(), TaskStatus.FINISHED_WITH_ERROR);
                            }
                        });
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

    @Override
    public void runTask(T task){
        taskQueue.add(task);
        taskStatusChangeListener.statusChange(task.getId(), TaskStatus.WAITING);
    }
}
