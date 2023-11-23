package com.sinter.cookspire.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationBeans {

    @Bean
    MessageSource messageSource() {

        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();

        msgSrc.setBasename("classpath:message");
        msgSrc.setDefaultEncoding("UTF-8");

        return msgSrc;

    }

    @Bean
    WebMvcConfigurer CORSConfig() {

        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:5000/");
            }
        };
    }

}
