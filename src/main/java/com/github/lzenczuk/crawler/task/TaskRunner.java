package com.github.lzenczuk.crawler.task;

import java.util.Optional;

/**
 * @author lzenczuk 11/01/2016
 */
public interface TaskRunner<T extends Task> {
    Optional<Result> run(T task);
}
