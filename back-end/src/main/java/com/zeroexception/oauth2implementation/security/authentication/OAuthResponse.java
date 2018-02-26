package com.zeroexception.oauth2implementation.security.authentication;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */


import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeroexception.oauth2implementation.security.domain.SecurityUser;

/**
 *  OAuthResponse send information about user, expire token to client side ( front-end side )
 */
public class OAuthResponse {

    private long expiresAt;

    @JsonProperty("user")
    private SecurityUser user;

    public OAuthResponse() {
    }

    @Override
    public String toString() {
        return "OAuthResponse{" +
                "expiresAt=" + expiresAt +
                ", user=" + user +
                '}';
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public SecurityUser getUser() {
        return user;
    }

    public OAuthResponse setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }
    public OAuthResponse setUser(SecurityUser user) {
        this.user = user;
        return this;
    }

}
