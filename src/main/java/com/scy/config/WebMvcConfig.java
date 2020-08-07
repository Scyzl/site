package com.scy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/site/").addResourceLocations("/");
        registry.addResourceHandler("/site/admin/**").addResourceLocations("classpath:/static/admin/");
        registry.addResourceHandler("/site/site/**").addResourceLocations("classpath:/static/site/");
        registry.addResourceHandler("/site/error/**").addResourceLocations("classpath:/static/site/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

        registry.addResourceHandler("/admin/").addResourceLocations("/");
        registry.addResourceHandler("/admin/admin/**").addResourceLocations("classpath:/static/admin/");
        registry.addResourceHandler("/admin/site/**").addResourceLocations("classpath:/static/site/");
        registry.addResourceHandler("/admin/error/**").addResourceLocations("classpath:/static/site/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

    }

}
