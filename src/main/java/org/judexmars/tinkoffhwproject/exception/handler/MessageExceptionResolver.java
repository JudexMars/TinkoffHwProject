package org.judexmars.tinkoffhwproject.exception.handler;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.judexmars.tinkoffhwproject.exception.BaseNotFoundException;
import org.judexmars.tinkoffhwproject.service.MessageRenderer;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageExceptionResolver extends DataFetcherExceptionResolverAdapter {

    private final MessageRenderer messageRenderer;

    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof BaseNotFoundException e) {
            return GraphQLError.newError()
                    .errorType(ErrorType.NOT_FOUND)
                    .message(messageRenderer.render(e.getMessageCode(), e.getArgs()))
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation()).build();
        }
        else {
            return null;
        }
    }
}
