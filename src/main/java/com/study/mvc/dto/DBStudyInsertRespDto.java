package com.study.mvc.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DBStudyInsertRespDto {

    private int id;
    private String name;
    private int age;
    private boolean successStatus;
    private int successCout;

}
