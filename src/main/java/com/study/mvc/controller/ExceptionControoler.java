package com.study.mvc.controller;


import com.study.mvc.exception.DuplicatedException;
import com.study.mvc.service.DBStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// 예외처리

@RestController
public class ExceptionControoler {

    @Autowired
    private DBStudyService dbStudyService;

    @PostMapping("/ex")
    public ResponseEntity<?> duplication(@RequestBody Map<String, String> map) {

        //if(dbStudyService.isDuplicatedd(map.get("name"))) {
//            return ResponseEntity.badRequest().body("중복된 이름입니다");
        //    throw new DuplicatedException("중복된 이름입니다");
        //}

        dbStudyService.checkDuplicatedByName(map.get("name"));

        return ResponseEntity.ok("중복되지 않은 이름입니다.");
    }
}
