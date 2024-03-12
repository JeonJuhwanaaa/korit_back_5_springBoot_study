package com.study.mvc.controller;


import com.study.mvc.dto.DBStudyReqDto;
import com.study.mvc.service.DBStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// controller -> service -> repository -> mybatis -> DB 경로
// 만드는 순서 controller -> repository
@RestController
public class DBController {

    @Autowired
    private DBStudyService dbStudyService;

    // @RequestBody : JSON 데이터를 받기위함
    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody DBStudyReqDto dbStudyReqDto) {

        System.out.println(dbStudyReqDto);

        return ResponseEntity.ok(dbStudyService.createStudy(dbStudyReqDto));
    }


    @GetMapping("/select/study/{id}")
    public ResponseEntity<?> selectStudy(@PathVariable int id) {

        return ResponseEntity.ok(dbStudyService.findStudyById(id));
    }

    @GetMapping("/select/study")    //Parameter로 불러올거라서 get 요청 시 ?name=김준일 이런식으로 불러올 것이다
    public ResponseEntity<?> selectStudy(@RequestParam String name) {
        return ResponseEntity.ok(dbStudyService.findStudyByName(name));
    }


    @GetMapping("/select/studys")
    public ResponseEntity<?> selectStudyAll() {
        return ResponseEntity.ok(dbStudyService.findAll());
    }


    @DeleteMapping("/delete/study/{id}")
    public ResponseEntity<?> deleteStudy(@PathVariable int id) {
        return ResponseEntity.ok(dbStudyService.deleteById(id));
    }

    // 업데이트 2가지(put / patch)
    // 매개변수 2개를 가진다
    // put은 전체 수정 - {nickname: aaa, password: 1234} => {nickname: "", password: 1111} / 결과: {nickname: "", password: 1111}
    @PutMapping("/update/study/{id}")
    public ResponseEntity<?> putStudy(@PathVariable int id,
                                         @RequestBody DBStudyReqDto dbStudyReqDto) {

        return ResponseEntity.ok(dbStudyService.putById(id, dbStudyReqDto));
    }

    // patch 는 부분 수정 - {nickname: aaa, password: 1234} => {nickname: "", password: 1111} / 결과: {nickname: aaa, password: 1111} / 빈칸은 변경사항 없는 것으로 인식
    @PatchMapping("/update/study/{id}")
    public ResponseEntity<?> patchStudy(@PathVariable int id,
                                         @RequestBody DBStudyReqDto dbStudyReqDto) {

        return ResponseEntity.ok(dbStudyService.patchById(id, dbStudyReqDto));
    }
}
