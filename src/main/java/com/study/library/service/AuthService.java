package com.study.library.service;

import com.study.library.dto.SigninReqDto;
import com.study.library.dto.SignupReqDto;
import com.study.library.entity.User;
import com.study.library.exception.SaveException;
import com.study.library.jwt.JwtProvider;
import com.study.library.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    public boolean isDuplicatedByUsername(String username) {
        return userMapper.findUserByUsername(username) != null;
    }

    //Transactional : 두개의 호출 중 하나라도 에러가 터지면 rollback 시키겠다 (DB에 추가 xx)
    @Transactional(rollbackFor = Exception.class)
    public void signup(SignupReqDto signupReqDto) {

        // DB에서 insert/ delete / 등등 리턴값이 기본적으로 int 라서 (select 는 빼고 다 int 리턴)
        int successCount = 0;

        // dto -> entity
        User user = signupReqDto.toEntity(passwordEncoder);

        // mapper 에서 .xmp을 호출하고 .xml 에서 sql 문으로 실행이 성공했다면 1
        successCount += userMapper.saveUser(user);
        successCount += userMapper.saveRole(user.getUserId(), 1);

        // 예외처리
        if(successCount < 2) {
            throw new SaveException();
        }
    }

    public String signin(SigninReqDto signinReqDto) {

        User user = userMapper.findUserByUsername(signinReqDto.getUsername());
        // 예외처리
        if(user == null) {
            throw new UsernameNotFoundException("사용자 정보를 확인하세요.");
        }if(!passwordEncoder.matches(signinReqDto.getPassword(), user.getPassword())) { // matches(암호화된 값을 풀어서 평문으로 만든다, 입력한 값) 두 매개변수를 비교
            throw new BadCredentialsException("사용자 정보를 확인하세요.");
        }
        // 토큰을 만들겠다
        return jwtProvider.generateToken(user);
    }
}
