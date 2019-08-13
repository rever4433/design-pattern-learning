package com.rever.pipeline.base;


import com.rever.pipeline.exception.PipelineException;

/**
 * @Description: 抽象的pipeline管理类
 * @Author: gaoyakang
 * @Version: 1.0
 * @Create Date Time: 2019-08-12 14:28
 * @Update Date Time:
 * @see
 */
public abstract class AbstractPipelineManager<Input, Output> {
    private final Pipeline<Output> pipeline;

    public AbstractPipelineManager(Pipeline<Output> pipeline) {
        this.pipeline = pipeline;
    }

    /**
     * pipeline 执行
     *
     * @param output 输出
     * @return 输出结果
     * @throws PipelineException 抛出pipeline异常
     */
    public Output execute(Input output) throws PipelineException {
        return pipeline.execute(output);
    }
}
