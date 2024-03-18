package com.study.library.config;


import com.study.library.security.exception.AuthEntryPoint;
import com.study.library.security.filter.JwtAuthenticationFilter;
import com.study.library.security.filter.PermitAllFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PermitAllFilter permitAllFilter;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthEntryPoint authEntryPoint;

    // 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 매개변수 http 안에 csrf / authorizeRequests / ... 넣는 작업
        http.cors();                                                // 크로스오리진 해줘야한다 ->
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/server/**", "/auth/**") // server로 시작하는 주소 , auth로 시작하는 주소
                .permitAll()                                      // 전부 허용해줘라
                .anyRequest()                                     // 그 외 나머지 어떤 요청은
                .authenticated()                                  // 인증 거쳐라
                .and()
                .addFilterAfter(permitAllFilter, LogoutFilter.class)        // 첫번째로 인증 시작
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)      // 여기까지 끝난 뒤
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint);

    }
}