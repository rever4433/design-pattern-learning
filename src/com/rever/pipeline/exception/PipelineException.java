package com.rever.pipeline.exception;

/**
 * @Description: pipeline 异常类
 * @Author: zhangzhuo
 * @Version: 1.0
 * @Create Date Time: 2018-07-24 14:03
 * @Update Date Time:
 *
 * @see
 */
public class PipelineException extends Exception{


    public PipelineException() {
        super();
    }

    public PipelineException(String message) {
        super(message);
    }

    public PipelineException(String message, Throwable cause) {
        super(message, cause);
    }

    public PipelineException(Throwable cause) {
        super(cause);
    }

    protected PipelineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
