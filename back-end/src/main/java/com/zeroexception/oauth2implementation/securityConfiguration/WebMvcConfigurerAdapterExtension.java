package com.zeroexception.oauth2implementation.securityConfiguration;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */

@Component
public class WebMvcConfigurerAdapterExtension extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins(
                "http://localhost:8000"
                ,"http://localhost:4200"
                ,"http://localhost:4000"
        );
    }
}
