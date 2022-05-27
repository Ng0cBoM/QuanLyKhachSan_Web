package com.hotel.demo.config;

import com.hotel.demo.interceptor.AuthInterceptor;
import com.hotel.demo.interceptor.RoleInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${server.path-file-update}")
    private String pathStoreImage;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");

        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:"+new File("").getAbsolutePath()+"\\"+this.pathStoreImage+"\\");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/login", "/css/**", "/js/**", "/images/**", "/auth/logout", "/user/register")
                .order(1);

        registry.addInterceptor(new RoleInterceptor())
                .addPathPatterns("/owner/**")
                .order(2);
    }


}