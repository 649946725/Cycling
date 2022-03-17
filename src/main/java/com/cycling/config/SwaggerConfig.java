package com.cycling.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


/**
 * @Author xpdxz
 * @ClassName SwaggerConfig
 * @Description TODO
 * @Date 2021/9/22 21:25
 * @Version 1.0
 */
@Configuration
public class SwaggerConfig {
    
    //为Swagger配置一个Docket实例
    @Bean
    public Docket restApi(Environment environment) {


        //设置要显示的swagger环境
        Profiles profiles = Profiles.of("dev");
        //获取当前运行的环境
        boolean falg = environment.acceptsProfiles(profiles);//监听当前使用的环境
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(falg)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cycling.controller"))
                .paths(PathSelectors.any()).build();

    }

    //配置swagger信息===>apiInfo
    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("骑行")
                .description("接口说明")
                .version("1.0.0").build();
    }
}
