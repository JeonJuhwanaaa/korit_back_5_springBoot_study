package com.study.mvc.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// @interface 쓰면 annotation 이 된다
//
@Retention(RetentionPolicy.RUNTIME)
// ElementType.TYPE : class 위에 어노테이션달수있다 / ElementType.METHOD : 메소드위에 달수있다 이라는 뜻
@Target({ElementType.METHOD})
public @interface ParamsAspect {
}
