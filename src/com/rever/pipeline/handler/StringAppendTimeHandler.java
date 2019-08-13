package com.rever.pipeline.handler;


import java.util.Date;

/**
 * @Description:
 * @Author: gaoyakang
 * @Version: 1.0
 * @Create Date Time: 2019-08-12 15:31
 * @Update Date Time:
 * @see
 */
public class StringAppendTimeHandler implements StringHandler {
    @Override
    public OutputControl<String> process(String inputBean) throws Exception {
        System.out.println(this.handlerName());
        return new OutputControl<String>(inputBean + new Date().getTime());
    }

    @Override
    public String handlerName() {
        return "StringAppendTimeHandler";
    }

    @Override
    public String handlerDescribe() {
        return "StringAppendTimeHandler";
    }
}
