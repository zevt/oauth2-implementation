package com.zeroexception.oauth2implementation.security.oauth2.token;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */

public interface SocialAccessToken {
    String getToken();
    String getProvider();
}
