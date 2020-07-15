package com.galid.study.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FilterLogging {

    @Before("execution(* org.springframework.security.web.access.intercept.FilterSecurityInterceptor.doFilter(..))")
    public void Hi() {
        System.out.println("FilterSecurity Interceptor doFilter!");
    }

    @Before("execution(* org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(..))")
    public void Hi2() {
        System.out.println("ExceptionTranslationFilter Interceptor doFilter!");
    }

}
