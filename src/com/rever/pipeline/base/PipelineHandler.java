package com.rever.pipeline.base;


import lombok.Data;

public interface PipelineHandler<Input,Output> {

    /**
     * 具体处理方法
     * @param inputBean 输入数据
     * @return 处理之后输出数据
     * @throws Exception 异常信息
     */
    OutputControl<Output> process(Input inputBean) throws Exception;

    /**
     * 处理者名称
     * @return 返回名称
     */
    String handlerName();

    /**
     * 处理者描述
     * @return 返回描述信息
     */
    String handlerDescribe();

    @Data
    class OutputControl<Output>{

        private Boolean isStopProcess = false;

        /**
         * 输出bean
         */
        private Output output;

        public OutputControl(Output output){
            this.output = output;
        }

        public OutputControl(Boolean isStopProcess){
            this.isStopProcess = isStopProcess;
        }

        public OutputControl(Output output, Boolean isStopProcess){
            this.output = output;
            this.isStopProcess = isStopProcess;
        }
    }


}
