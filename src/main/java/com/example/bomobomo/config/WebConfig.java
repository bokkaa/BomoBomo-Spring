package com.example.bomobomo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.eventImg}")
    private String eventReviewImg;

    @Value("${file.eventDetail}")
    private String eventDetailImg;

    @Value("${file.empImg}")
    private String empPics;

    @Value("${file.dir}")
    private String eventReview;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceHandlers() 리소스 경로와 연결된 URL경로를 작성한다.
        //리소스는 자원(이미지)
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + eventReviewImg)
                .addResourceLocations("file:" + eventDetailImg)
                .addResourceLocations("file:" + empPics)
                .addResourceLocations("file:"+ eventReview);
        //로컬 디스크 경로는 file: 을 반드시 사용해야한다.
        registry.addResourceHandler("/common/**")
                .addResourceLocations("classpath:/static/common/");
    }
}