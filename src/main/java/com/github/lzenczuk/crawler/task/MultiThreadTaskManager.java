package com.github.lzenczuk.crawler.task;

import com.github.lzenczuk.crawler.task.notification.TaskNotification;
import com.github.lzenczuk.crawler.task.notification.TaskNotificationListener;

import java.util.Date;
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
    private final TaskNotificationListener taskNotificationListener;

    public MultiThreadTaskManager(TaskRunner runner, TaskNotificationListener taskNotificationListener) {
        this(DEFAULT_NUMBER_OF_THREADS, runner, taskNotificationListener);
    }

    public MultiThreadTaskManager(int threads, TaskRunner runner, TaskNotificationListener taskNotificationListener) {
        this.runner = runner;
        this.taskNotificationListener = taskNotificationListener;
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
                        taskNotificationListener.update(new TaskNotification(task.getId(), new Date(), TaskStatus.RUNNING, "MultiThreadTaskManager", "Executing using scriptRunner"));
                        final Optional<Result> resultOptional = runner.run(task);

                        resultOptional.ifPresent(result -> {
                            if(result.isSuccess()){
                                taskNotificationListener.update(new TaskNotification(task.getId(), new Date(), TaskStatus.SUCCESS, "MultiThreadTaskManager", "Executed successfully"));
                            }else{
                                taskNotificationListener.update(new TaskNotification(task.getId(), new Date(), TaskStatus.ERROR, "MultiThreadTaskManager", "Execution error: "+result.getErrorMessage()));
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
        taskNotificationListener.update(new TaskNotification(task.getId(), new Date(), TaskStatus.RUNNING, "MultiThreadTaskManager", "Added to execution queue"));
    }
}
