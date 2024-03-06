package com.study.mvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.study.mvc.diAndIoc.DiRepository;
import com.study.mvc.diAndIoc.DiService;
import com.study.mvc.diAndIoc.IoCService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
//@RequiredArgsConstructor
// 사용 시 final 을 붙여줘야한다
public class IoCController {

    @Autowired
    private IoCService ioCService;

    @GetMapping("/ioc")
    public ResponseEntity<?> iocTest() throws JsonProcessingException {
        // iocService 는 null 이니깐 nullpointException 오류 뜨니깐 getJson 예외처리하기
        String json = ioCService.getJson();

        return ResponseEntity.ok(json);
    }
}
