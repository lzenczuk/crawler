package com.github.lzenczuk.crawler.app;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzenczuk 15/01/2016
 */
@Configuration
public class SpringConfig {

    /**
     * Load jackson module responsible for supporting JDK8 classes, like optional
     * @return jackson module
     */
    @Bean
    public Module parameterNamesModule() {
        return new Jdk8Module();
    }
}
