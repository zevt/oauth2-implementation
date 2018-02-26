package com.zeroexception.oauth2implementation.security.oauth2.manager;

import com.zeroexception.oauth2implementation.security.oauth2.token.SocialAccessToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */

public interface SocialAuthenticationManager extends AuthenticationManager {
    Authentication authenticate(SocialAccessToken socialAccessToken);
}
