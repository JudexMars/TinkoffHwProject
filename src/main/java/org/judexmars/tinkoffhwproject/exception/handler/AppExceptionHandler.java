package org.judexmars.tinkoffhwproject.exception.handler;

import lombok.RequiredArgsConstructor;
import org.judexmars.tinkoffhwproject.exception.BaseNotFoundException;
import org.judexmars.tinkoffhwproject.exception.UploadFailedException;
import org.judexmars.tinkoffhwproject.service.MessageRenderer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import java.util.List;
import java.util.Objects;

@RestControllerAdvice
@RequiredArgsConstructor
public class AppExceptionHandler {

    private final MessageRenderer messageRenderer;

    @ExceptionHandler(BaseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(BaseNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, messageRenderer.render(ex.getMessageCode(), ex.getArgs())));
    }

    @ExceptionHandler(UploadFailedException.class)
    public ResponseEntity<ErrorResponse> handleUploadFailedException(UploadFailedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, messageRenderer.render(ex.getMessageCode())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> body =
                ex.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .filter(Objects::nonNull)
                        .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400, body.toString()));
    }

    public record ErrorResponse(int status, String message) { }
}
