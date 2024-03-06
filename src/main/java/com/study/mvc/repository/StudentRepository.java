package com.study.mvc.repository;

import java.util.List;

public interface StudentRepository {

    //interface에서 추상메소드는 기본적으로 public 이다 (생략가능하지만 되도록이면 적어주자)

    public List<String> getStudentListAll();
    public String findStudentNameByIndex(int index);
}
