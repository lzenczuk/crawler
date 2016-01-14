package com.github.lzenczuk.crawler.app.service;

import org.springframework.stereotype.Service;

/**
 * @author lzenczuk 14/01/2016
 */
@Service
public class NanoTimeTasksIdGeneratorImpl implements TasksIdGenerator {

    @Override
    public long generateId(){
        return System.nanoTime();
    }
}
