package com.github.lzenczuk.crawler.task;

import com.github.lzenczuk.crawler.browser.Browser;
import com.github.lzenczuk.crawler.task.script.ScriptTask;
import com.github.lzenczuk.crawler.task.status.TaskStatusChangeListener;
import org.junit.Test;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author lzenczuk 12/01/2016
 */
public class MultiThreadTaskManagerTest {

    @Test
    public void executeTaskInQueue() throws Exception {

        final CountDownLatch countDownLatch = new CountDownLatch(4);

        final Browser browser = mock(Browser.class);

        final TaskRunner taskRunner = mock(TaskRunner.class);
        when(taskRunner.run(any())).then(invocation -> {
            countDownLatch.countDown();
            Thread.sleep(100);
            return Optional.of(Result.success());
        });

        final TaskStatusChangeListener taskStatusChangeListener = mock(TaskStatusChangeListener.class);

        final MultiThreadTaskManager taskQueue = new MultiThreadTaskManager(taskRunner, taskStatusChangeListener);
        taskQueue.runTask(new ScriptTask(0, "test", null));
        taskQueue.runTask(new ScriptTask(1, "test", null));
        taskQueue.runTask(new ScriptTask(2, "test", null));
        taskQueue.runTask(new ScriptTask(3, "test", null));

        countDownLatch.await(5, TimeUnit.SECONDS);

        verify(taskRunner, times(4)).run(any());
    }
}
