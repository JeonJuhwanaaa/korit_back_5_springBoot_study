package com.study.mvc.controller;


import com.study.mvc.aop.annotation.ParamsAspect;
import com.study.mvc.dto.ParamsTestDto;
import com.study.mvc.service.AOPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @Slf4j : 오류의 기록을 남길수있는 가장 많이 쓰는 것 / log를 쓸수있게해준다 (.info() .error() 많이씀)/ System.out.println(); 이랑 비슷
// 사용법 : log.info("로그: {} {}", -9999, 1000); 중괄호 안에 순서대로 들어간다

@Slf4j
@RestController
public class AOPController {

    @Autowired
    private AOPService aopService;

    // @ParamsAspect 쓰므로써 paramsTest 가 호출되면 바로 Aop로 간다
    @ParamsAspect
    @GetMapping("/aop/params")
    public ResponseEntity<?> paramsTest(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer option) {

//        log.info("로그: {} {}", -9999, 1000);
//        log.error("로그: {} {}", -9999, 1000);

        // 데이터베이스에서 정보 조회
//        log.error("params: {}", paramsTestDto);

        aopService.test("김준일", 31);

        return ResponseEntity.ok(null);
    }
}
