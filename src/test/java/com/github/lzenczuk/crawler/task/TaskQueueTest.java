package com.github.lzenczuk.crawler.task;

import com.github.lzenczuk.crawler.browser.Browser;
import com.github.lzenczuk.crawler.script.Result;
import com.github.lzenczuk.crawler.script.ScriptRunner;
import org.junit.Test;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * @author lzenczuk 12/01/2016
 */
public class TaskQueueTest {

    @Test
    public void executeTaskInQueue() throws Exception {

        final CountDownLatch countDownLatch = new CountDownLatch(4);

        final Browser browser = mock(Browser.class);

        final ScriptRunner scriptRunner = mock(ScriptRunner.class);
        when(scriptRunner.run(eq("test"), isNull(Map.class), same(browser))).then(invocation -> {
            countDownLatch.countDown();
            Thread.sleep(100);
            return Optional.of(Result.success());
        });

        final TaskQueue taskQueue = new TaskQueue(browser, scriptRunner);
        taskQueue.addTask(new Task(null, "test"));
        taskQueue.addTask(new Task(null, "test"));
        taskQueue.addTask(new Task(null, "test"));
        taskQueue.addTask(new Task(null, "test"));

        countDownLatch.await(5, TimeUnit.SECONDS);

        verify(scriptRunner, times(4)).run(eq("test"), isNull(Map.class), same(browser));
    }
}
