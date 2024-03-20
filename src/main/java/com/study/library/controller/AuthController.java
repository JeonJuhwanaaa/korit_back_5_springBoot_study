package com.study.library.controller;


import com.study.library.aop.annotation.ParamsPrintAspect;
import com.study.library.aop.annotation.ValidAspect;
import com.study.library.dto.SigninReqDto;
import com.study.library.dto.SignupReqDto;
import com.study.library.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// csr 할때 데이터를 주고 받고 이동하겠다 / @postMapping, @GetMapping ... 등
@RestController
// @RequestMapping : 공통 주소이기때문에 그룹화한것
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // @valid + Binding 세트

    // 잘못된 정보 확인용
    @ValidAspect
    @PostMapping("/signup")
    // 검증걸과가 bindingresult에 담긴다
    // @Valid 객체에 대한 검증을 하는 어노테이션 / 순서대로 SignupReqDto 검증 하고 BindingResult 검증
    // BindingResult 는 검증하면서 오류 발생 시 잡아내서 message 보여지는 용도
    public ResponseEntity<?> signup(@Valid @RequestBody SignupReqDto signupReqDto, BindingResult bindingResult) {

//        // true면 중복이라는 것
//        if(authService.isDuplicatedByUsername((signupReqDto.getUsername()))) {
//
//            ObjectError objectError = new FieldError("username","username" ,"이미 존재하는 사용자이름 입니다.");
//            bindingResult.addError(objectError);
////            Map<String, String> errorMap = Map.of("username", "이미 존재하는 사용자이름 입니다.");
////            return ResponseEntity.badRequest().body(errorMap);
//        }

        authService.signup(signupReqDto);

        return ResponseEntity.created(null).body(true);
    }

    // id / password 찾기만하는게 아니라 token 발급도 해줘야해서 post 요청
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninReqDto signinReqDto) {
        return ResponseEntity.ok(authService.signin(signinReqDto));
    }

}
