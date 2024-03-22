package com.study.library.controller;

import com.study.library.aop.annotation.ValidAspect;
import com.study.library.dto.EditPasswordReqDto;
import com.study.library.security.PrincipalUser;
import com.study.library.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/principal")
    // 토큰 정보를 가져오겠다
    // 그냥 라이브러리의 메소드를 가져온 것 = 그저 토큰(통행증)을 만들기위한 설정
    // Authentication : 통행증
    public ResponseEntity<?> getPrincipal() {
        // user -> principal -> authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();         //알아서 검증하고 통행증에 권한 정보를 담는다
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();                    // 사용자에대한 정보를 가져와서 다운케스팅한 후 principalUser에 담는다
        return ResponseEntity.ok(principalUser);
        // return 값이 프론트 안에 then(response) 안으로 들어가는 순서
    }

    @ValidAspect
    @PutMapping("/password")
    public ResponseEntity<?> editPassword(@Valid @RequestBody EditPasswordReqDto editPasswordReqDto, BindingResult bindingResult) {

        accountService.editPassword(editPasswordReqDto);

        return ResponseEntity.ok(true);
    }
}
