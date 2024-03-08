package com.study.mvc.dto;

import com.study.mvc.entity.Study;
import lombok.Data;

@Data
public class DBStudyReqDto {

    private String name;
    private int age;

    public Study toEntity(int id) {
        return Study.builder()
                .id(id)
                .name(name)
                .age(age)
                .build();
    }
}
