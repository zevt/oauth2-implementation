package com.zeroexception.oauth2implementation.securityConfiguration;

import com.zeroexception.oauth2implementation.security.oauth2.filter.SocialOauth2Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.security.SecureRandom;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String SALT = "tremolo"; // Salt should be protected carefully

    @Autowired
    private UserDetailsService userDetailsService;

    private SocialOauth2Filter socialOauth2Filter;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public WebSecurityConfiguration(SocialOauth2Filter socialOauth2Filter) {
        this.socialOauth2Filter = socialOauth2Filter;
    }

    @Override
    @Order(Ordered.HIGHEST_PRECEDENCE)
    protected void configure(HttpSecurity http) throws Exception {

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                .addFilterBefore(this.socialOauth2Filter, BasicAuthenticationFilter.class)
                .addFilterBefore(this.socialOauth2Filter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // To verify SSL with provider
                .antMatchers("v1/test").permitAll()
                .antMatchers("/v1/secure/**").hasAnyRole("BASIC")
                .antMatchers("/oauth2","/oauth2/**").permitAll()
                .antMatchers("/user", "/user/**").hasAnyRole("BASIC", "PREMIUM", "ADMIN")
                .antMatchers("/premium", "/premium/**").hasAnyRole("PREMIUM")
                .anyRequest().authenticated()
                .and()
                .httpBasic().disable()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }


    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
