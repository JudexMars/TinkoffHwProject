package org.judexmars.tinkoffhwproject.exception.handler;

import lombok.RequiredArgsConstructor;
import org.judexmars.tinkoffhwproject.exception.BaseNotFoundException;
import org.judexmars.tinkoffhwproject.service.MessageRenderer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class BaseNotFoundExceptionHandler {

    private final MessageRenderer messageRenderer;

    @ExceptionHandler(BaseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(BaseNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(messageRenderer.render(ex.getMessageCode(), ex.getArgs())));
    }

    public record ErrorResponse(String message) { }
}
