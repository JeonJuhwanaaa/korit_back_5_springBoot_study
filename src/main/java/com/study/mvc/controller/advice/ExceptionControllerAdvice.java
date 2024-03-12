package com.study.mvc.controller.advice;

import com.study.mvc.exception.DuplicatedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    // 에외가 터지면 다 중단하고 여기로 온다
    @ExceptionHandler(DuplicatedException.class)
    public ResponseEntity<?> duplicatedExceptionHandle(DuplicatedException e) {
        return ResponseEntity.badRequest().body(e.getErrorMap());
    }
}
