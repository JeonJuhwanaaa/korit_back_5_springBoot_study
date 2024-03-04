package com.study.mvc.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentRespDto {

        private String name;
        private String age;
        private String phone;
        private String address;
}
