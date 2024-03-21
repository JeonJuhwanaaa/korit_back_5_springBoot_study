package com.study.library.service;

import com.study.library.entity.RoleRegister;
import com.study.library.jwt.JwtProvider;
import com.study.library.repository.UserMapper;
import com.study.library.security.PrincipalUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Objects;

@Service
public class AccountMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserMapper userMapper;

    // spring.mail.address = 발신자 이메일주소
    @Value("${spring.mail.address}")
    private String fromMailAddress;

    // server.deploy-address = localhost
    @Value("${server.deploy-address}")
    private String serverAddress;

    // port = 8080
    @Value("${server.port}")
    private String serverPort;

    public boolean sendAuthMail() {
        boolean result = false;

        // 라이브러리 세팅
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();

        int userId = principalUser.getUserId();
        String toMailAddress = principalUser.getEmail();

        // 이메일 전송할려면 MimeMessage를 사용 - 라이브러리 세팅
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            // 첫번째 매개변수 :
            // 두번째 매개변수 : 파일 전송 시에는 true
            // 세번째 매개변수 :
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            helper.setSubject("도서관리 시스템 사용자 메일 인증");            // 메일 보낼 때 제목
            helper.setFrom(fromMailAddress);                            // secret.yml 에 입력해놓은 이메일주소 (발신자)
            helper.setTo(toMailAddress);                                // 회원가입했을 때 입력했던 이메일주소 (수신자)

            String authMailToken = jwtProvider.generateAuthMailToken(userId, toMailAddress);        // 회원가입된 정보(수신자)의 Id / email 가져오기


            // 메일 내용작성
            // StringBuilder 문자열 하나하나씩 쌓는 용도
            StringBuilder mailContent = new StringBuilder();

            mailContent.append("<div>");
            mailContent.append("<h1>계정 활성화 절차</h1>");
            mailContent.append("<h3>해당 계정을 인증하기 위해 아래의 버튼을 클릭해 주세요</h3>");

            // http://localhost:8080/mail/authenticate?authToken=JWT토큰 라는 뜻
            mailContent.append("<a href=\"http://" + serverAddress + ":" + serverPort + "/mail/authenticate?authToken=" + authMailToken + "\" style=\"border: 1px solid #dbdbdb; padding: 10px 20px; text-decoration: none; background-color: white; color: #222222;\" >인증하기</a>");
            mailContent.append("</div>");

            // 첫번째 매개변수
            // 두번째
            // 세번째
            mimeMessage.setText(mailContent.toString(), "utf-8", "html");
            javaMailSender.send(mimeMessage);   // 메일 전송
            result = true;
        } catch (MessagingException e) {        // 메일 전송실패 시
            // throw new RuntimeException(e);
            e.printStackTrace();                // 어떤 오류인지 내용 확인
        }
        return result;
    }

    public Map<String, Object> authenticate(String token) {
        Claims claims = null;
        Map<String, Object> resultMap = null;

        // << error 종류 >>
        // ExpiredJwtException => 토큰 유효기간 만료
        // MalformedJwtException => 위조, 변조
        // SignatureException => 형식, 길이 오류
        // IllegalArgumentException => null 또는 빈값

        try{
            // 정상적으로 인증이 됐을 경우
            claims = jwtProvider.getClaims(token);
            int userId = Integer.parseInt(claims.get("userId").toString());
            RoleRegister roleRegister = userMapper.findRoleRegisterByUserIdAndRoleId(userId, 2);

            // 이미 해당 userId / roleId 를 가지고있다면
            if(roleRegister != null) {
                resultMap = Map.of("status", true, "message", "이미 인증이 완료된 메일입니다.");
            }else {
                // 없다면 2(일반 사용자 권한)을 넣어준다
                userMapper.saveRole(userId,2);
                resultMap = Map.of("status", true, "message", "인증완료");
            }

        }catch(ExpiredJwtException e) {
            resultMap = Map.of("status", false, "message", "인증 시간을 초과하였습니다. \n인증 메일을 다시 받으세요.");
        }catch(JwtException e) {
            resultMap = Map.of("status", false, "message", "잘못된 접근입니다, \n인증 메일을 다시 받으세요.");
        }

        return resultMap;
    }
}