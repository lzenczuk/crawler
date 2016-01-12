package com.github.lzenczuk.crawler.script;

/**
 * @author lzenczuk 12/01/2016
 */
public class ResultBuilder {

    public Result success(){
        return Result.success();
    }

    public Result error(String errorMessage){
        return Result.error(errorMessage);
    }
}
