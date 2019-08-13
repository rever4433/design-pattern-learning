package com.rever.pipeline.handler;

import com.rever.pipeline.base.PipelineHandler;

/**
 * @Description:
 * @Author: gaoyakang
 * @Version: 1.0
 * @Create Date Time: 2019-08-12 15:33
 * @Update Date Time:
 * @see
 */
public class StringAppendDotHandler implements StringHandler {
    @Override
    public PipelineHandler.OutputControl<String> process(String inputBean) throws Exception {
        System.out.println(this.handlerName());
        return new OutputControl<String>(inputBean+".");
    }

    @Override
    public String handlerName() {
        return "StringAppendDotHandler";
    }

    @Override
    public String handlerDescribe() {
        return "StringAppendDotHandler";
    }
}
