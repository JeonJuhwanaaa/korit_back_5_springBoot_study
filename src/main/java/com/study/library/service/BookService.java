package com.study.library.service;


import com.study.library.dto.RegisterBookReqDto;
import com.study.library.dto.SearchBookCountRespDto;
import com.study.library.dto.SearchBookReqDto;
import com.study.library.dto.SearchBookRespDto;
import com.study.library.entity.Book;
import com.study.library.repository.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookMapper bookMapper;

    @Transactional(rollbackFor = Exception.class)       // insert 했다가 취소할 시 롤백 될수있게 해준다
    public void saveBook(RegisterBookReqDto registerBookReqDto) {
        bookMapper.saveBook(registerBookReqDto.toEntity());
    }


    // limit 때 사용 , 페이지 1 설정
    public List<SearchBookRespDto> searchBooks(SearchBookReqDto searchBookReqDto) {

        int startIndex = (searchBookReqDto.getPage() - 1) * searchBookReqDto.getCount();    // ex) 1page = 1~20, 2page = 21~40

        List<Book> books = bookMapper.findBooks(
                startIndex,
                searchBookReqDto.getCount(),
                searchBookReqDto.getBookTypeId(),
                searchBookReqDto.getCategoryId(),
                searchBookReqDto.getSearchTypeId(),
                searchBookReqDto.getSearchText()
        );
        return books.stream().map(Book::toSearchBookRespDto).collect(Collectors.toList());

        // -----------------  stream이 아닌 다른 방법으로 변환해주기  -----------------

//      1, << 향상된 for문으로 entity를 -> dto 로 변환하기>>
//      List<SearchBookRespDto> list = new List<>();
//      for(Book book : books {
//          Book.toSearchBookRespDto(book);
//      })

//      2, << for문으로 entity를 -> dto 로 변환하기 >>
//      List<SearchBookRespDto> list = new List<>();
//      for(int i = 0; i < books.size(); i++) {
//          list.add(books[i].toSearchBookRespDto());
//      }
    }

    public SearchBookCountRespDto getBookCount(SearchBookReqDto searchBookReqDto) {
        int bookCount = bookMapper.getBookCount(
                searchBookReqDto.getBookTypeId(),
                searchBookReqDto.getCategoryId(),
                searchBookReqDto.getSearchTypeId(),
                searchBookReqDto.getSearchText()
        );

        int maxPageNumber = (int) Math.ceil(((double) bookCount) / searchBookReqDto.getCount());

        return SearchBookCountRespDto.builder()
                .totalCount(bookCount)
                .maxPageNumber(maxPageNumber)
                .build();
    }
}
