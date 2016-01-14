package com.github.lzenczuk.crawler.task;

/**
 * @author lzenczuk 12/01/2016
 */
public class Result {
    final private boolean success;
    final private String errorMessage;

    private Result(boolean success, String errorMessage) {
        this.errorMessage = errorMessage;
        this.success = success;
    }

    public static Result success(){
        return new Result(true, "");
    }

    public static Result error(String errorMessage){
        return new Result(false, errorMessage);
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isError() {
        return !success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
