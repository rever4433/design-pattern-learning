package com.rever.pipeline.handler;


/**
 * @Description:
 * @Author: gaoyakang
 * @Version: 1.0
 * @Create Date Time: 2019-08-12 15:28
 * @Update Date Time:
 * @see
 */
public class StringReverseHandler implements StringHandler {
    @Override
    public OutputControl<String> process(String inputBean) throws Exception {
        System.out.println(this.handlerName());
        return new OutputControl<String>(new StringBuilder(inputBean).reverse().toString());
    }

    @Override
    public String handlerName() {
        return "StringReverseHandler";
    }

    @Override
    public String handlerDescribe() {
        return "StringReverseHandler";
    }
}
