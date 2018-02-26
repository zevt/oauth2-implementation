package com.zeroexception.oauth2implementation.securityConfiguration;

import com.zeroexception.oauth2implementation.security.oauth2.filter.SocialOauth2Filter;
import com.zeroexception.oauth2implementation.security.oauth2.manager.SocialAuthenticationManager;
import com.zeroexception.oauth2implementation.security.oauth2.manager.SocialAuthenticationManagerImpl;
import com.zeroexception.oauth2implementation.services.TokenTrackerService;
import com.zeroexception.oauth2implementation.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */

@Configuration
public class ResourceConfigurationBeans {

    private static Logger logger = LoggerFactory.getLogger(ResourceConfigurationBeans.class);

    @Autowired
    private TokenTrackerService tokenTrackerService;

    @Autowired
    private UserService userService;

    @Bean
    public SocialAuthenticationManager socialAuthenticationManager() {
        SocialAuthenticationManagerImpl socialAuthenticationManager = new SocialAuthenticationManagerImpl();
        socialAuthenticationManager.setTrackerService(this.tokenTrackerService)
        .setUserService(this.userService);

        return socialAuthenticationManager;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Primary
    public SocialOauth2Filter socialOauth2Filter() {
        SocialOauth2Filter socialOauth2Filter = new SocialOauth2Filter();
        socialOauth2Filter.setAuthenticationManager(this.socialAuthenticationManager());
        return socialOauth2Filter;
    }

}
