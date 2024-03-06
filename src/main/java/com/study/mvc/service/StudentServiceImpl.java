package com.study.mvc.service;

import com.study.mvc.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    @Override
    public List<?> getStudentList() {
        // 리스트안에 맵이 들어가기위함 [{}, {}, {}]
        List<Map<String, String>> studentMapList = new ArrayList<>();
        List<String> studentList = studentRepository.getStudentListAll();

        for(String studentName : studentList) {
            studentMapList.add(Map.of("name", studentName));
        }
        return studentMapList;
    }

    @Override
    public Object getStudent(int index) {
        String studentName = studentRepository.findStudentNameByIndex(index);
        return Map.of("name", studentName);
    }
}