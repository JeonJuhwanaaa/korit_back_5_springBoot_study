package com.study.library.dto;

import com.study.library.entity.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
// 정규식 표현을 사용해서 유효성 검사
public class SignupReqDto {

    @Pattern(regexp = "^[A-Za-z0-9]{4,10}$", message = "영문자, 숫자 5 ~ 10자리 형식이어야 합니다." )
    private String username;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{7,128}$", message = "하나의 영문자, 숫자, 특수문자를 포함한 5~128자리 형식이어야 합니다.")
    private String password;

    @Pattern(regexp = "^[가-힣]{1,}$", message = "한글문자 형식이어야 합니다.")
    private String name;
    // @NotBlank :
//    @NotBlank
    @Email(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{1,3}$", message = "이메일 형식이어야 합니다.")
    private String email;

    // dto -> entity 변환
    // BCrypt 로 감싸주면 암호화가 됨
    public User toEntity(BCryptPasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .name(name)
                .email(email)
                .build();
    }
}
