package com.rever.pipeline;

import com.rever.pipeline.base.AbstractPipelineManager;
import com.rever.pipeline.base.Pipeline;
import com.rever.pipeline.exception.PipelineException;
import com.rever.pipeline.handler.StringAppendDotHandler;
import com.rever.pipeline.handler.StringAppendTimeHandler;
import com.rever.pipeline.handler.StringReverseHandler;

public class StringPipelineManager extends AbstractPipelineManager<String, String> {

    public StringPipelineManager(StringAppendDotHandler stringAppendDotHandler,
                                 StringAppendTimeHandler stringAppendTimeHandler,
                                 StringReverseHandler stringReverseHandler) {

        super(Pipeline.<String>builder()
                .next(stringReverseHandler, Pipeline.<String>builder()
                        .next(stringAppendTimeHandler)
                        .next(stringAppendDotHandler))
                .next(stringAppendTimeHandler, Pipeline.<String>builder()
                        .next(stringReverseHandler)
                        .next(stringAppendDotHandler))
                .next(stringAppendDotHandler, Pipeline.<String>builder()
                        .next(stringReverseHandler)
                        .next(stringAppendDotHandler)));
    }

    public static void main(String[] args) {
        StringAppendDotHandler stringAppendDotHandler = new StringAppendDotHandler();
        StringAppendTimeHandler stringAppendTimeHandler = new StringAppendTimeHandler();
        StringReverseHandler stringReverseHandler = new StringReverseHandler();
        StringPipelineManager stringPipeline = new StringPipelineManager(stringAppendDotHandler, stringAppendTimeHandler, stringReverseHandler);
        try {
            System.out.println(stringPipeline.execute("I want to eat"));
        } catch (PipelineException e) {
            e.printStackTrace();
        }
    }

}
