package com.zeroexception.oauth2implementation.security.oauth2.token;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */


public interface SocialDetailsFromToken {

    String getEmail();

    String getName();

    String getFirstName();

    String getLastName();
    String getId();
    long expiresAtEpochSecond();

}
