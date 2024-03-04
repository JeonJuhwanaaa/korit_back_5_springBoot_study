package com.study.mvc.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

// AllArgsConstructor 쓸 땐 필요한 값이 모두 들어가 있어야 한다 값이 하나라도 빠지면 오류
// NoArgsConstructor 쓰면 빈 값을 넣어줘도 오류xx (NoArgsConstructor 쓰는거나 그냥 안쓰는거나 같음)
public class HelloDto {

    private String name;
    private int age;

}
