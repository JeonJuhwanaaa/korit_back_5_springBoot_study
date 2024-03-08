package com.study.mvc.repository;

import com.study.mvc.entity.Study;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// Repository에는 @Mapper 를 달아줄것이다
// 기능별로 테이블이 하나고 Repository안에는 interface 하나만 만들것이다

@Mapper
public interface DBStudyRepository {
    // insert 성공횟수라서 리턴값을 int 로 준 것
    // 매개변수가 entity가 되어야한다

    // insert
    public int save(Study study);

//    -------------------------------------
    // select
    // 단일 검색 - 아이디 기준
    public Study findStudyById(int id);
    // 단일 검색 - 이름 기준(유니크 키값)
    public Study findStudyByName(String name);
    // 여러개 검색
    public List<Study> findAll();
    // 지우기
    public int deleteById(int id);
    // 업데이트 2가지
    public int putById(Study study);
    public int patchById(Study study);
}
