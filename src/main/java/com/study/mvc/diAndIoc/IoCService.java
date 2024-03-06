package com.study.mvc.diAndIoc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
//@RequiredArgsConstructor
public class IoCService {

    // @Component 어노테이션 사용하고 private 위 @Autowired 사용하면 new IocRepository 를 생성하므로 싱글톤 특징과 같은 역할을 한다
    @Autowired
    private IoCRepository ioCRepository;

    public String getJson() throws JsonProcessingException {

        Map<String, String> nameMap = ioCRepository.convertNameMap();
        ObjectMapper objectMapper = new ObjectMapper();
        // writeValueAsString 예외처리하기
        return objectMapper.writeValueAsString(nameMap);
    }
}
