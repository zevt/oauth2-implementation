package com.zeroexception.oauth2implementation.security.authentication;

import com.google.common.base.Preconditions;
import com.zeroexception.oauth2implementation.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */


public class SocialAuthentication implements Authentication {

    private static final Log logger = LogFactory.getLog(SocialAuthentication.class);

    private User user;

    public SocialAuthentication(User user) {
        this.user = Preconditions.checkNotNull(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return this.user.getPassword();
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.user;
    }

    @Override
    public boolean isAuthenticated() {
        return user.isAccountNonLocked();
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return this.user.getUsername();
    }
}
