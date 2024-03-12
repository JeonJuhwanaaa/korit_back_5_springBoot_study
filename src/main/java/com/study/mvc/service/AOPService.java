package com.study.mvc.service;


import com.study.mvc.aop.annotation.ParamsAspect;
import com.study.mvc.aop.annotation.TimeAspect;
import org.springframework.stereotype.Service;

@Service
public class AOPService {

    @TimeAspect
    public String test(String name, int age) {

        return "이름: " + name + ", 나이: " + age;
    }
}
