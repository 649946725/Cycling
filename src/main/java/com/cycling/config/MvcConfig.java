package com.cycling.config;

import com.cycling.utils.DataTypeFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author xpdxz
 * @ClassName MvcConfig
 * @Description TODO
 * @Date 2021/10/27 16:12
 * @Version 1.0
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DataTypeFormatter());
    }
}
