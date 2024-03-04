package com.study.mvc.controller;
import com.study.mvc.dto.HelloDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;


@RestController
public class StudyRestContoller {

//    @GetMapping("/hello/test")
//    public String hello(@RequestParam String name) {
//
//        System.out.println(name);
//
//        return "Hello";
//    }

//    @GetMapping("/hello/test")
//    public String hello(String name) {
//
//        System.out.println(name);
//
//        return "Hello";
//    }

    // int 로 매개변수 넣어줘도 형변환 해줄필요 없다 알아서 parseInt 해줌
    @GetMapping("/hello/test")
    public String hello(HelloDto helloDto) {

        System.out.println(helloDto);

        return "Hello";
    }
}
