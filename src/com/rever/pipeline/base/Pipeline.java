 package com.rever.pipeline.base;

 import com.rever.pipeline.exception.PipelineException;
 import lombok.Builder;
 import lombok.Data;

 import java.util.ArrayList;
 import java.util.List;
 import java.util.Objects;
 import java.util.Optional;
 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.Executors;

 /**
  * @Description: pipeline构造类
  * @Author: gaoyakang
  * @Version: 1.0
  * @Create Date Time: 2018-07-24 14:03
  * @Update Date Time:
  *
  * @param <T> 该 Pipeline 所输出的数据类型
  * @see
  */
 public class Pipeline<T> {
     private final List<Rule> ruleList;
     private final ExecutorService executorService;

     private Pipeline(ExecutorService executorService){
         this.ruleList = new ArrayList<>();
         if(executorService == null){
             //使用了newCachedThreadPool线程池
             this.executorService = Executors.newCachedThreadPool();
         } else {
             this.executorService = executorService;
         }
     }

     /**
      * 构造pipeline
      * @param executorService 多分支线程池
      * @param <T>
      * @return
      */
     public static <T> Pipeline<T> builder(ExecutorService executorService){
         return new Pipeline<T>(executorService);
     }

     /**
      * 构造pipeline
      * @param <T>
      * @return
      */
     public static <T> Pipeline<T> builder(){
         return new Pipeline<T>(null);
     }


     /**
      * 添加下一个处理对象
      * @param pipelineHandler 处理对象
      * @return 当前对象
      */
     public Pipeline<T> next(PipelineHandler pipelineHandler){
         Objects.requireNonNull(pipelineHandler);
         List<Pipeline> pipelineList = null;
         return this.next(pipelineHandler, pipelineList);
     }

     /**
      * 添加下一个处理对象，可以新增一个分支pipeline
      * @param pipelineHandler 处理对象
      * @param pipeline 当前分支出的pipeline
      * @return 当前对象
      */
     public Pipeline<T> next(PipelineHandler pipelineHandler, Pipeline pipeline) {
         Objects.requireNonNull(pipelineHandler);
         Objects.requireNonNull(pipeline);
         List<Pipeline> pipelines=new ArrayList<>();
         pipelines.add(pipeline);
         System.out.println(pipeline.ruleList.size());
         return this.next(pipelineHandler,pipelines);
     }

     /**
      * 添加下一个处理对象，可以新增多个分支pipeline
      * @param pipelineHandler 处理对象
      * @param pipelineList 当前分支出的pipeline list
      * @return 当前对象
      */
     public Pipeline<T> next(PipelineHandler pipelineHandler, List<Pipeline> pipelineList){
         Objects.requireNonNull(pipelineHandler);
         Rule rule = Rule.builder()
                 .pipelineHandler(pipelineHandler)
                 .currentRuleNextHandlerRules(pipelineList)
                 .build();
         this.ruleList.add(rule);
         return this;
     }

     /**
      * 执行pipeline
      * @param input 要被处理的对象
      * @return 当前pipeline处理后的数据
      * @throws PipelineException
      */
     public T execute(Object input) throws PipelineException {
         if(input == null){
             throw new PipelineException("inputBean can't be null");
         }
         for (Rule rule : ruleList) {
             PipelineHandler.OutputControl process = null;
             final PipelineHandler pipelineHandler = rule.getPipelineHandler();
             try {
                 process = pipelineHandler.process(input);
                 if(process == null){
                     throw new PipelineException("Handler.OutputControl process is null");
                 }
                 if(process.getIsStopProcess()){
                     // 停止处理链
                     break;
                 }
                 input = Optional.ofNullable(process.getOutput())
                         .orElseThrow(()->new PipelineException("process -> outputBean is null"));
                 final List<Pipeline> currentRuleNextHandlerRules = rule.getCurrentRuleNextHandlerRules();
                 if(currentRuleNextHandlerRules != null){
                     for (Pipeline handlerRule : currentRuleNextHandlerRules) {
                         executorService.execute(new HandlerRuleBranch(handlerRule, input));
                     }
                 }
             } catch (Exception e){
                 e.printStackTrace();
                 throw new PipelineException(pipelineHandler.handlerName() + " -> error : " + e.getMessage());
             }
         }
         return (T)input;
     }

     @Builder
     @Data
     private static class Rule{
         private PipelineHandler pipelineHandler;
         private List<Pipeline> currentRuleNextHandlerRules;
     }

     /**
      * 实现了Runnable类的方法
      */
     private class HandlerRuleBranch implements Runnable{

         private final Object inputBean;
         private final Pipeline pipeline;

         public HandlerRuleBranch(Pipeline pipeline, Object inputBean) {
             this.inputBean = inputBean;
             this.pipeline = pipeline;
         }

         @Override
         public void run() {
             try {
                 final Object execute = pipeline.execute(inputBean);
             } catch (PipelineException e) {
                 e.printStackTrace();
             }
         }
     }

 }
