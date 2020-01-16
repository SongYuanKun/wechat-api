package com.songyuankun.wechat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author songyuankun
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("系统API")
                        .description("")
                        .termsOfServiceUrl("songyuankun.top")
                        .version("1.0")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.songyuankun.wechat.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket blogApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("博客API")
                .apiInfo(new ApiInfoBuilder()
                        .title("博客API")
                        .description("博客前端用到的")
                        .termsOfServiceUrl("songyuankun.top")
                        .version("1.0")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.songyuankun.wechat.controller.blog"))
                .paths(PathSelectors.any())
                .build();
    }

}
