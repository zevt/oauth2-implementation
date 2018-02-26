package com.zeroexception.oauth2implementation.security.oauth2.filter;

import com.google.common.base.Preconditions;
import com.zeroexception.oauth2implementation.security.oauth2.Constant;
import com.zeroexception.oauth2implementation.security.oauth2.manager.SocialAuthenticationManager;
import com.zeroexception.oauth2implementation.security.oauth2.token.SocialAccessToken;
import com.zeroexception.oauth2implementation.security.oauth2.token.SocialTokenExtractor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */


public class SocialOauth2Filter implements Filter, InitializingBean {

    private static Log log = LogFactory.getLog(SocialOauth2Filter.class);
    private static SocialTokenExtractor tokenExtractor = new SocialTokenExtractor();
    private SocialAuthenticationManager authenticationManager;

    public SocialOauth2Filter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        SocialAccessToken socialAccessToken = tokenExtractor.extractSocialAccessToken(request);
        try {
            Authentication authentication = this.authenticationManager.authenticate(socialAccessToken);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                request.setAttribute(Constant.TOKEN_TRACKER_KEY,socialAccessToken.getProvider() + "_" + socialAccessToken.getToken());
            } else {
                SecurityContextHolder.clearContext();
                request.setAttribute(Constant.TOKEN_TRACKER_KEY,"");
            }

        } catch (AuthenticationException exception) {
            log.debug("AuthenticationException occurs ");
            SecurityContextHolder.clearContext();
            exception.printStackTrace();
            request.setAttribute(Constant.TOKEN_TRACKER_KEY,"");
        }

        filterChain.doFilter(request, response);
    }

    public SocialOauth2Filter setAuthenticationManager(SocialAuthenticationManager authenticationManager) {
        this.authenticationManager = Preconditions.checkNotNull(authenticationManager);
        return this;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Preconditions.checkNotNull(authenticationManager, " AuthenticationManager cannot be null");
    }

    @Override
    public void destroy() {

    }
}
