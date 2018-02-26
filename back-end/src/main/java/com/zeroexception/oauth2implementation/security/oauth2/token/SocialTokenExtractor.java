package com.zeroexception.oauth2implementation.security.oauth2.token;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.security.oauth2.common.OAuth2AccessToken.ACCESS_TOKEN;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */

public class SocialTokenExtractor implements TokenExtractor {
    private static final Log logger = LogFactory.getLog(SocialTokenExtractor.class);


    public SocialTokenExtractor() {
    }

    @Override
    @Deprecated
    public Authentication extract(HttpServletRequest httpServletRequest) {
        String tokenValue = this.extractToken(httpServletRequest);
        if (tokenValue != null) {
            return new PreAuthenticatedAuthenticationToken(tokenValue, "");
        } else {
            return null;
        }
    }


    public SocialAccessToken extractSocialAccessToken(HttpServletRequest request) {
        String tokenValue = this.extractToken(request);
        if (tokenValue != null) {
            logger.debug("Token = " + tokenValue);
            return new SocialAccessTokenImpl(tokenValue);
        } else {
            logger.debug("Token is null");
            return null;
        }
    }


    protected String extractToken(HttpServletRequest request) {
        String token = this.extractHeaderToken(request);
        if (token == null) {
            logger.debug("Token not found in headers. Trying request parameters.");
            token = request.getParameter(ACCESS_TOKEN);
            if (token == null) {
                logger.debug("Token not found in request parameters.  Not an OAuth2 request.");
            } else {
                request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE, ACCESS_TOKEN);
            }
        }

        return token;
    }

    protected String extractHeaderToken(HttpServletRequest request) {
        return request.getHeader(ACCESS_TOKEN);
    }

}
