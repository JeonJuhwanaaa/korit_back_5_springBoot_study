package com.study.mvc.service;

import com.study.mvc.dto.StudentExDto;
import com.study.mvc.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl2 implements StudentService{

    private final StudentRepository studentRepository;

    @Override
    public List<?> getStudentList() {
        // 리스트안에 맵이 들어가기위함 [{}, {}, {}]
        List<StudentExDto> studentExDtoList = new ArrayList<>();
        List<String> studentList = studentRepository.getStudentListAll();

        for(String studentName : studentList) {
            studentExDtoList.add(new StudentExDto(studentName));
        }
        return studentExDtoList;
    }

    @Override
    public Object getStudent(int index) {
        String studentName = studentRepository.findStudentNameByIndex(index);
        return new StudentExDto(studentName);
    }
}