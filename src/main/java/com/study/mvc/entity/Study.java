package com.study.mvc.entity;

import com.study.mvc.dto.DBStudySelectRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


// <<경로>>  클라이언트 -> (JSON형태가 Dto형태) ->controller -> Service -> (Service에서 Dto형태를 Entity 형태(table이랑 같은 형태)로) -> Repository -> DB


// @NoArgs / @AllArgs 항상 만들어줄 것
// @Builder 쓰는거면 @AllArgs / @NoArgs 지워줘야 됨

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

// sql 테이블 똑같이 가져올 것
public class Study {
    private int id;
    private String name;
    private int age;
    private LocalDateTime createDate;

//    여러개 검색
//    위 id,name,age 를 가지고 씀
    public DBStudySelectRespDto toDto() {
        return DBStudySelectRespDto.builder()
                .id(id)
                .name(name)
                .age(age)
                .build();
    }
}
