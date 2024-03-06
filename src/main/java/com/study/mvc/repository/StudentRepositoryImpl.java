package com.study.mvc.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.mvc.diAndIoc.IoCRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class StudentRepositoryImpl implements StudentRepository{

    private List<String> studentList = List.of("전주환", "서창현", "예홍렬");

    @Override
    public List<String> getStudentListAll() {
        return studentList;
    }

    @Override
    public String findStudentNameByIndex(int index) {
        return studentList.get(index);
    }
}