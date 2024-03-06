package com.study.mvc.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

// @Configuration 도 @Component 다
// 특징: Bean 수동 등록(2개 이상 등록가능)
// Configurtation 안에서만 Bean 쓸수 있음

@Configuration
public class BeanConfig {

    // return 되는 값이 ioc 등록된다
    // component 명은 메소드 명
    // ObjectMapper 안에 Component 가 없으니 @Autowired 로 쓸려면 @Bean으로 ioc container에 넣어주기위함
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
