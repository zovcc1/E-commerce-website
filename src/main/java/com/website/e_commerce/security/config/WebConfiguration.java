package com.website.e_commerce.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {
//    public static final String INDEX_VIEW_NAME = "forward:/auth/login.html";
//
//
//
//    public void addViewControllers(final ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName(INDEX_VIEW_NAME);
//        registry.addViewController("/auth/login").setViewName(INDEX_VIEW_NAME);
//        registry.addViewController("/auth/logout").setViewName(INDEX_VIEW_NAME);

//}

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
    @Bean
    public ViewResolver viewResolver() {
        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
        viewResolver.setViewClass(InternalResourceView.class);
        return viewResolver;
    }

}
