package com.example.bomobomo.config;

import com.example.bomobomo.interceptor.AdminLoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//WebMvcConfigurer 인터페이스는 스프링 MVC 웹 설정을 정의할 수 있는 인터페이스이다.
//인터셉터 설정을 위해 필요하다.
@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
    private final AdminLoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistry 객체는 인터셉터를 등록 및 관리하는 객체이다.
//        addInterceptor() : 우리가 만든 인터셉터를 등록한다.
//        addPathPatterns() : URL패턴을 등록하여 특정 URL에서 인터셉터가 실행되도록 설정한다.
//        excludePathPatterns() : 제외시킬 URL을 등록한다.
//        경로 설정 시 /*과 /**의 차이
//        /*은 하위 경로 1개 /user/login
//        /**은 하위 경로 1개 이상 /user/v1/login

        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/admin/*")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/logout")
                .excludePathPatterns("/admin/*")
                .order(1) ;
    }
}
