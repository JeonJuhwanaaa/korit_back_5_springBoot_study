package com.study.library.security.filter;


import com.study.library.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends GenericFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

            Boolean isPermitAll = (Boolean) request.getAttribute("isPermitAll");

            // 인증 과정
            // 로컬스토리지안에 있는 Authorization
            if(!isPermitAll) {
                String accessToken = request.getHeader("Authorization");
//            System.out.println(accessToken);

            String removedBearerToken = jwtProvider.removeBearer(accessToken);
            Claims claims = jwtProvider.getClaims(removedBearerToken);

            if(claims == null) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());        //401오류(인증실패) 랑 같다
                return;
            }

            Authentication authentication = jwtProvider.getAuthentication(claims);

            if(authentication == null) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());        //401오류(인증실패) 랑 같다
                return;
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 전처리
        filterChain.doFilter(request, response);
        // 후처리
    }
}
