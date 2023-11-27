package com.sinter.cookspire.utils;

import java.util.ArrayList;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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

    public ApplicationBeans(MappingJackson2HttpMessageConverter convertor) {
        var supportedMediaTypes = new ArrayList<>(convertor.getSupportedMediaTypes());
        supportedMediaTypes.add(new MediaType("application", "octet-stream"));
        convertor.setSupportedMediaTypes(supportedMediaTypes);
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
