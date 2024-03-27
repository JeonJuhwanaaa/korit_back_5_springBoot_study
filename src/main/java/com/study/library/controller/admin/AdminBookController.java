package com.study.library.controller.admin;


import com.study.library.aop.annotation.ParamsPrintAspect;
import com.study.library.dto.RegisterBookReqDto;
import com.study.library.dto.SearchBookReqDto;
import com.study.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminBookController {

    @Autowired
    private BookService bookService;

    // 추가
    @ParamsPrintAspect
    @PostMapping("/book")       // 도서 추가
    public ResponseEntity<?> saveBook(@Valid @RequestBody RegisterBookReqDto registerBookReqDto, BindingResult bindingResult) {

        bookService.saveBook(registerBookReqDto);

        return ResponseEntity.created(null).body(null);     // 웹 f12 화면으로 보여진다
    }

    // 조회
    // 주소창에 ?page=1 로 찾는것이 -> param
    @GetMapping("/books")       // 선택해서 요청 날리면 실행
    public ResponseEntity<?> searchBooks(SearchBookReqDto searchBookReqDto) {           // param 은 @RequestBody 사용 안해도된다
        return ResponseEntity.ok(bookService.searchBooks(searchBookReqDto));
    }

    // 페이지 번호
    @GetMapping("/books/count")
    public ResponseEntity<?> getCount(SearchBookReqDto searchBookReqDto) {
        return ResponseEntity.ok(bookService.getBookCount(searchBookReqDto));
    }

}
