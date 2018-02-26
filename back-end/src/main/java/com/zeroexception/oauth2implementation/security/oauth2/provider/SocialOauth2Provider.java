package com.zeroexception.oauth2implementation.security.oauth2.provider;


import com.zeroexception.oauth2implementation.security.oauth2.token.SocialDetailsFromToken;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */

public interface SocialOauth2Provider {
    SocialDetailsFromToken getSocialDetails(String accessToken);
}
