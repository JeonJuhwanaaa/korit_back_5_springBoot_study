package com.study.library.service;


import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OAuth2PrincipalUserService implements OAuth2UserService {

    // 로그인 인증 서비스
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

//      System.out.println(attributes);
        String provider = userRequest.getClientRegistration().getClientName();  // Google, Kakao, Naver

        Map<String, Object> newAttributes = null;
        switch (provider) {
            case "Google":
                String id = attributes.get("sub").toString();
                newAttributes = Map.of("id", id, "provider", provider);      // 구글은 sub , 네이버 카카오는 id 로 표기되기때문에 sub을 id로 바꿔줄 필요있음()
                break;
            case "Naver":
                break;
            case "Kakao":
                break;
        }

        // 첫번째 매개변수는 - 권한 / 세번째는 key값
        return new DefaultOAuth2User(oAuth2User.getAuthorities(), newAttributes, "id");
    }
}
