package com.scy.config;

import com.scy.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author Scy
 * @Date 2020/8/7 15:37
 * @Version 1.0
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 配置URL资源处理器
     *
     * @param registry 注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/site/").addResourceLocations("classpath:/static/site/");
        registry.addResourceHandler("/site/**").addResourceLocations("classpath:/static/site/");
        registry.addResourceHandler("/site/admin/**").addResourceLocations("classpath:/static/admin/");
        registry.addResourceHandler("/site/blog/**").addResourceLocations("classpath:/static/site/");
        registry.addResourceHandler("/site/blog/site/**").addResourceLocations("classpath:/static/site/");
        registry.addResourceHandler("/site/site/**").addResourceLocations("classpath:/static/site/");
        registry.addResourceHandler("/site/tags/**").addResourceLocations("classpath:/static/site/");
        registry.addResourceHandler("/site/tags/site/**").addResourceLocations("classpath:/static/site/");
        registry.addResourceHandler("/site/comments/**").addResourceLocations("classpath:/static/site/");
        registry.addResourceHandler("/site/error/**").addResourceLocations("classpath:/static/site/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

        registry.addResourceHandler("/admin/**").addResourceLocations("classpath:/static/admin/");
        registry.addResourceHandler("/admin/tags/**").addResourceLocations("classpath:/static/admin/");
        registry.addResourceHandler("/admin/tags/add/**").addResourceLocations("classpath:/static/admin/");
        registry.addResourceHandler("/admin/tags/delete/**").addResourceLocations("classpath:/static/admin/");
        registry.addResourceHandler("/admin/types/**").addResourceLocations("classpath:/static/admin/");
        registry.addResourceHandler("/admin/types/add/**").addResourceLocations("classpath:/static/admin/");
        registry.addResourceHandler("/admin/types/delete/**").addResourceLocations("classpath:/static/admin/");
        registry.addResourceHandler("/admin/blogs/**").addResourceLocations("classpath:/static/admin/");
        registry.addResourceHandler("/admin/blogs/add/**").addResourceLocations("classpath:/static/admin/");
        registry.addResourceHandler("/admin/blogs/delete/**").addResourceLocations("classpath:/static/admin/");
        registry.addResourceHandler("/admin/login/**").addResourceLocations("classpath:/static/admin/");
        registry.addResourceHandler("/admin/site/**").addResourceLocations("classpath:/static/site/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/static/**", "/**/*.css", "/**/*.js", "/**/*.jpg", "/**/*.png", "/**/font/*",
                        "/**/*.woff", "/**/*.woff2", "/**/*.ttf", "/**/*.svg");
    }
}
