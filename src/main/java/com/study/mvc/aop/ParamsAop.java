package com.study.mvc.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import java.security.CodeSigner;

// aop는 항상 @Aspect 달아주기

@Slf4j
@Aspect
@Component
public class ParamsAop {

    // @annotation("annotation 경로" 넣어주기)
    @Pointcut("@annotation(com.study.mvc.aop.annotation.ParamsAspect)")
    private void pointCut() {}

    // 매개변수 ProceedingJoinPoint 무조건 고정
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        // proceed 예외처리해주기
        // proceed 자체가 Object를 리턴
        // proceed 전에 쓰면 전처리 후에쓰면 후처리

        // 값을 가져온다
        Object[] args = proceedingJoinPoint.getArgs();

        // 변수의 이름을 가져오기
        CodeSignature codeSignature = (CodeSignature) proceedingJoinPoint.getSignature();
        String[] paramsName = codeSignature.getParameterNames();

        // 클래스명 , 메소드명 가져오기
        String className = codeSignature.getDeclaringTypeName();
        String methodName = codeSignature.getName();

        for(int i = 0; i < args.length; i++) {
            log.info("{}.{} >>> {}: {}",className, methodName, paramsName[i], args[i]);
        }

//        log.info("전처리");

        Object result = proceedingJoinPoint.proceed();  //비지니스 로직

//        log.info("후처리");
//        log.info("response: {}", result);

        return result;

    }
}
