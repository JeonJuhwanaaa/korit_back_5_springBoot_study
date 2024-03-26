package com.study.library.repository;


import com.study.library.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {
    public int saveBook(Book book);
    public List<Book> findBooks(
            @Param("startIndex") int startIndex,        // 몇번부터         (limit 때 사용
            @Param("count") int count,                  // 몇개씩 들고와라     (limit 때 사용
            @Param("bookTypeId") int bookTypeId,
            @Param("categoryId") int categoryId,
            @Param("searchTypeId") int searchTypeId,
            @Param("searchText") String searchText);
}
