package com.study.mvc.controller;

import com.study.mvc.repository.StudentRepository;
import com.study.mvc.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StudentRestController {

    @Autowired
    @Qualifier("studentServiceImpl2")
    private StudentService studentService;

    @GetMapping("/ex/students")
    public ResponseEntity<?> students() {

        return ResponseEntity.ok(studentService.getStudentList());
    }

    @GetMapping("/ex/student/{index}")
    public ResponseEntity<?> student(@PathVariable int index) {

        return ResponseEntity.ok(studentService.getStudent(index));
    }
}
