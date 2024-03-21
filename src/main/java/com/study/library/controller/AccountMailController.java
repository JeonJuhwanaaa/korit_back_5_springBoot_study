package com.study.library.controller;

import com.study.library.service.AccountMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/mail")
public class AccountMailController {

    @Autowired
    private AccountMailService accountMailService;

    //
    @PostMapping("/send")
    @ResponseBody
    public ResponseEntity<?> send() {
        return ResponseEntity.ok(accountMailService.sendAuthMail());
    }

    // 받은 메일 인증하기 버튼 누르면 나오는 창
    // @RequestParam : 주소창에 ?=
    @GetMapping("/authenticate")    // ssr 사용
    public String resultPage(Model model, @RequestParam String authToken) {
        Map<String, Object> resultMap = accountMailService.authenticate(authToken);
        model.addAllAttributes(resultMap);
        return "result_page";       // 파일 경로로 지정 - .html 파일을 자체를 리턴값
    }
}