package com.github.lzenczuk.crawler.task;

/**
 * @author lzenczuk 14/01/2016
 */
public interface TaskManager<T extends Task> {
    void runTask(T task);
}
