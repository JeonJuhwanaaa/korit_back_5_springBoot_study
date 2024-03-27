package com.study.library.dto;


import com.study.library.entity.Book;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class RegisterBookReqDto {

    private String isbn;
    @Min(value = 1, message = "숫자만 입력 가능합니다.")  // 유효성 검사 , @Min - 최소글자 , message - 실패시 알림
    private int bookTypeId;
    @Min(value = 1, message = "숫자만 입력 가능합니다.")
    private int categoryId;
    @NotBlank(message = "도서명은 빈 값일 수 없습니다.")    // 공백, null, 띄어쓰기
    // @NotNull - null 체크 (null이 아니어야한다)
    // @Null - null 체크 (null 이어야한다)
    // @Empty - 공백만 체크 null(x)
    private String bookName;
    @NotBlank(message = "저자명은 빈 값일 수 없습니다")
    private String authorName;
    @NotBlank(message = "출판사명은 빈 값일 수 없습니다")
    private String publisherName;
    @NotBlank(message = "커버 이미지는 빈 값일 수 없습니다")
    private String coverImgUrl;

    public Book toEntity() {
        return Book.builder()
                .bookName(bookName)
                .authorName(authorName)
                .publisherName(publisherName)
                .isbn(isbn)
                .bookTypeId(bookTypeId)
                .categoryId(categoryId)
                .coverImgUrl(coverImgUrl)
                .build();
    }
}
