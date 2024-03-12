package com.study.mvc.exception;

import lombok.Getter;
import java.util.Map;

public class DuplicatedException extends RuntimeException{

    @Getter
    private Map<String, String> errorMap;

    public DuplicatedException(String message) {
        // Cntl + o 해서 받아오기
        super(message);

        errorMap = Map.of("message", message);
    }
}
