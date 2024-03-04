package com.study.mvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.mvc.dto.StudentReqDto;
import com.study.mvc.entity.Student;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller 명: StudentController
 *
 * 메소드명: getStudentInfo()
 * 요청 메소드: Get
 * 요청 URL: /student
 * 요청 Params: name, age, phone, address
 * 응답데이터: JSON(name, age, phone, address)
 *
 * */


@RestController
public class StudentController {

    // @RequestBody 붙이면 JSON으로 가져온다
    // post 할 땐 @RequestBody 꼭 붙이기
    // cookie 저장소 사용하기 :
    @PostMapping("/student")
    public ResponseEntity<?> addStudent(@CookieValue String students, @RequestBody Student student) throws JsonProcessingException {

        List<Student> studentList = new ArrayList<>();
        int lastId = 0;

        if(students != null) {
            if(!students.isBlank()) {
                ObjectMapper studentCookie = new ObjectMapper();
                studentList = studentCookie.readValue(students, List.class);
                lastId = studentList.get(studentList.size() - 1).getStudentId();
            }
        }

        student.setStudentId(lastId + 1);
        studentList.add(student);

        ObjectMapper newStudentList = new ObjectMapper();
        String newStudents = newStudentList.writeValueAsString(studentList);
        ResponseCookie responseCookie = ResponseCookie
                .from("test", "test_data")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60)
                .build();

        // ("큰따옴표)문자 저장x

        return ResponseEntity
                .created(null)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(student);
    }







    // ---------------------------------------------------------------------------
//    @GetMapping("/student")
//    public Object getStudentInfo(StudentReqDto studentReqDto) {
//        System.out.println(studentReqDto);
//
//        return studentReqDto.toRespDto();
//    }

    @GetMapping("/student")
    public ResponseEntity<?> getStudentInfo(StudentReqDto studentReqDto) {
        System.out.println(studentReqDto);

        // ResponseEntitiy 는 응답데이터: ok / badRequest /
        return ResponseEntity.ok().body(studentReqDto.toRespDto());
    }

    // @pathVariable 은 주소창에 ? 뒤 적는게 아니라
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudent(@PathVariable int studentId) {
        List<Student> studentList = List.of(
                new Student(1, "김준일"),
                new Student(2, "김준이"),
                new Student(3, "김준삼"),
                new Student(4, "김준사")
        );

        // 1. 원초적인 for문 으로
        Student findStudent = null;
        for(Student student : studentList) {
            if(student.getStudentId() == studentId) {
                findStudent = student;
            }
        }
        if(findStudent == null) {
            return ResponseEntity.badRequest().body(Map.of("errorMessage", "존재하지않는 ID입니다"));
        }
        // ---------------------------------------------------------------------------

        // 2. filter -1
        studentList.stream().filter(student -> student.getStudentId() == studentId).collect(Collectors.toList()).get(0);
        // ---------------------------------------------------------------------------

        // 3. filter -2
        Optional<Student> optionalstudent =
                studentList.stream().filter(student -> student.getStudentId() == studentId).findFirst();

        if(optionalstudent.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("errorMessage", "존재하지않는 ID입니다"));
        }

        findStudent = optionalstudent.get();

        // ---------------------------------------------------------------------------


        return ResponseEntity.ok().body(findStudent);
    }

}
