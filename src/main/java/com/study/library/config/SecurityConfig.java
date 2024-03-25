package com.study.library.config;


import com.study.library.security.exception.AuthEntryPoint;
import com.study.library.security.filter.JwtAuthenticationFilter;
import com.study.library.security.filter.PermitAllFilter;
import com.study.library.security.handler.OAuth2SuccessHandler;
import com.study.library.service.OAuth2PrincipalUserService;
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

@EnableWebSecurity // 3
@Configuration // 1
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PermitAllFilter permitAllFilter;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Autowired
    private OAuth2PrincipalUserService oAuth2PrincipalUserService;

    @Autowired
    private OAuth2SuccessHandler oAuth2SuccessHandler;

    // 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override // 2
    protected void configure(HttpSecurity http) throws Exception {

        // 매개변수 http 안에 csrf / authorizeRequests / ... 넣는 작업
        http.cors();                                                // cors 정책 허용
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/server/**", "/auth/**") // server로 시작하는 주소 , auth로 시작하는 주소
                .permitAll()                                      // 전부 허용해줘라
                .antMatchers("/mail/authenticate")
                .permitAll()
                .antMatchers("/admin/**")
                .hasRole("ADMIN")
                .anyRequest()                                     // 그 외 나머지 어떤 요청은
                .authenticated()                                  // 인증 거쳐라

                .and()
                .addFilterAfter(permitAllFilter, LogoutFilter.class)        // 첫번째로 인증 시작
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)      // 여기까지 끝난 뒤
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .and()

                .oauth2Login()
                .successHandler(oAuth2SuccessHandler)               // 통합로그인성공하면 이쪽으로 이동
                .userInfoEndpoint()                                 // OAuth2로그인 토큰 검사 (oauth2 동작방식 검색해서 보면 됨)
                .userService(oAuth2PrincipalUserService);
    }
}
