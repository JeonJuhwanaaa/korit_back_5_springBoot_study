package com.study.library.jwt;


import com.study.library.entity.User;
import com.study.library.repository.UserMapper;
import com.study.library.security.PrincipalUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Slf4j      // log, error 콘솔창에 뜨게할려고 쓴다
@Component
public class JwtProvider {

//    -------------------------------------------------------------------------- 고정
    private final Key key;
    private UserMapper userMapper;

    // 생성될 때 야놀에서 secret 값을가져온다
    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Autowired UserMapper userMapper) {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.userMapper = userMapper;
    }
//    --------------------------------------------------------------------------
    
    public String generateToken(User user) {

        int userId = user.getUserId();
        String username = user.getUsername();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        // 만료기간 = 현재 날짜 시간을 가지고와서 하루(24시간) 더해라
        Date expireDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 20));

        // 토큰 만들기
        // Claim : 토큰의 대한 정보를 set 해주는것 (jwt 버전 setter 느낌)
        String accessToken = Jwts
                .builder()
                .claim("userId", userId)                    // key, value
                .claim("username", username)                // key, value
                .claim("authorities", authorities)          // 권한
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS256)       // 암호화
                .compact();                                    // builder 마무리

        return accessToken;
    }

    // ---------------------------------------------------------------------------------

    public String removeBearer(String token) {
        // hasText() 안에 token을 넣어줘서 null / 공백 체크 알아서 해줌
        if(!StringUtils.hasText(token)) {
            return null;
        }

        // 토큰이 제대로 들어왔으면
        // subString(0, 5) : 문자열 0인덱스부터 5까지만 잘라라
        // Bearer 만 짤라야하니깐
        return token.substring("Bearer ".length());
    }

    public Claims getClaims(String token) {
        Claims claims = null;

        claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)      // token 안에있는 claim 들을 Claim으로 변환
                .getBody();                 //
        return claims;
    }

    public Authentication getAuthentication(Claims claims) {
        String username = claims.get("username").toString();
        // DB에서 name 찾아서 가져오기
        User user = userMapper.findUserByUsername(username);

        if(user == null) {
            // 토큰은 유효하지만 db에서 user정보가 삭제되었을 경우
            return null;
        }

        PrincipalUser principalUser = user.toPrincipalUser();
        return new UsernamePasswordAuthenticationToken(principalUser, principalUser.getPassword(), principalUser.getAuthorities());
    }

    // ---------------------------------------------------------------------------------

    public String generateAuthMailToken(int userId, String toMailAddress) {
        Date expireDate = new Date(new Date().getTime() + (1000 * 60 * 5));
        return Jwts
                .builder()
                .claim("userId", userId)
                .claim("toMailAddress", toMailAddress)
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
