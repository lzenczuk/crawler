package com.github.lzenczuk.crawler.task.notification.task;

import com.github.lzenczuk.crawler.task.status.cache.TaskEntry;
import com.github.lzenczuk.crawler.task.status.TaskStatus;
import com.github.lzenczuk.crawler.task.status.TaskStatusChange;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SynchronizedDescriptiveStatistics;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author lzenczuk 13/01/2016
 */
public class TaskEntryPerformanceTest {

    @Test
    public void testPerformance() throws Exception {

        TaskEntry te = new TaskEntry(22);

        final int NUMBER_OF_WRITE_THREADS = 1;
        final String WRITE_THREAD_NAME = "TP_WriteThread_";
        final int WRITES_ITERATIONS = 100000;

        final int NUMBER_OF_READ_THREADS = 3;
        final String READ_THREAD_NAME = "TP_ReadThread_";
        final int READS_ITERATIONS = 10000;

        CyclicBarrier cb = new CyclicBarrier(NUMBER_OF_WRITE_THREADS+NUMBER_OF_READ_THREADS);
        CountDownLatch cdl = new CountDownLatch(NUMBER_OF_WRITE_THREADS+NUMBER_OF_READ_THREADS);

        DescriptiveStatistics addStatusChangeTimes = new SynchronizedDescriptiveStatistics();
        DescriptiveStatistics getChangeHistoryTimes = new SynchronizedDescriptiveStatistics();
        DescriptiveStatistics getLatestChangeTimes = new SynchronizedDescriptiveStatistics();

        final Runnable writeRunnable = () -> {
            try {
                cb.await();

                System.out.println("Write task started");

                final Random random = new Random(System.nanoTime());

                for (int y = 0; y < WRITES_ITERATIONS; y++) {
                    final int i = random.nextInt(3);

                    final long startTime = System.nanoTime();

                    if (i == 0) {
                        te.addStatusChange(new TaskStatusChange(22, TaskStatus.WAITING, new Date()));
                    } else if (i == 1) {
                        te.addStatusChange(new TaskStatusChange(22, TaskStatus.RUNNING, new Date()));
                    } else if (i == 2) {
                        te.addStatusChange(new TaskStatusChange(22, TaskStatus.FINISHED_SUCCESFULY, new Date()));
                    } else {
                        te.addStatusChange(new TaskStatusChange(22, TaskStatus.FINISHED_WITH_ERROR, new Date()));
                    }

                    addStatusChangeTimes.addValue(System.nanoTime()-startTime);
                }
                System.out.println("Write task ended");

            } catch (InterruptedException e) {
                System.out.println("Error: "+e.getMessage());
            } catch (BrokenBarrierException e) {
                System.out.println("Error: "+e.getMessage());
            }finally {
                cdl.countDown();
            }
        };

        final Runnable readRunnable = () -> {
            try {
                cb.await();

                System.out.println("Read task started");

                final Random random = new Random(System.nanoTime());

                for (int y = 0; y < READS_ITERATIONS; y++) {
                    final int i = random.nextInt(10);

                    final long startTime = System.nanoTime();

                    if (i == 0) {
                        te.getChangeHistory();
                        getChangeHistoryTimes.addValue(System.nanoTime()-startTime);
                    }else{
                        te.getLatestChange();
                        getLatestChangeTimes.addValue(System.nanoTime()-startTime);
                    }
                }

                System.out.println("Read task ended");

            } catch (InterruptedException e) {
                System.out.println("Error: "+e.getMessage());
            } catch (BrokenBarrierException e) {
                System.out.println("Error: "+e.getMessage());
            }finally {
                cdl.countDown();
            }
        };


        createTestThreads(NUMBER_OF_WRITE_THREADS, WRITE_THREAD_NAME, writeRunnable);
        createTestThreads(NUMBER_OF_READ_THREADS, READ_THREAD_NAME, readRunnable);

        System.out.println("Tasks ready.");
        cdl.await();
        System.out.println("Done\n");

        System.out.println("addStatusChange");
        System.out.println(addStatusChangeTimes);

        System.out.println("getLatestChange");
        System.out.println(getLatestChangeTimes);

        System.out.println("getChangeHistory");
        System.out.println(getChangeHistoryTimes);

    }

    private void createTestThreads(int NUMBER_OF_THREADS, final String THREAD_NAME, Runnable todo) {
        if(NUMBER_OF_THREADS==0) return;

        final ExecutorService writeES = Executors.newFixedThreadPool(NUMBER_OF_THREADS, new ThreadFactory() {

            private int counter = 0;

            @Override
            public Thread newThread(Runnable r) {
                final Thread thread = new Thread(r);
                thread.setName(THREAD_NAME + counter);
                counter++;

                return thread;
            }
        });

        for(int x=0;x<NUMBER_OF_THREADS; x++){
            writeES.submit(todo);
        }
    }
}
