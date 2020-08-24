package com.scy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @Author Scy
 * @Date 2020/8/24 12:15
 * @Version 1.0
 */

@Configuration
@EnableSwagger2     // 开启 swagger2
public class SwaggerConfig {

    // 配置 Swagger 的 Docket 实例
    @Bean
    public Docket docket(Environment environment) {
        // 设置要显示的 Swagger 环境
        Profiles profiles = Profiles.of("dev", "test");

        // 获取项目的环境
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("Scy")
                .enable(flag)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.scy.controller"))
                .build();
    }


    //配置 swagger 的 ApiInfo 内容
    private ApiInfo apiInfo() {

        Contact contact = new Contact("Scy", "http://blog.scy.com", "1769171934@qq.com");
        return new ApiInfo(
                "Swagger-Api 文档",
                "Scy-site 的 Swagger-Api 文档",
                "1.0",
                "http://blog.scy.com",
                contact,
                "Apache 2.0",
                "http://apache.org",
                new ArrayList<>()
        );
    }
}
