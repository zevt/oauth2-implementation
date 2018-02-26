package com.zeroexception.oauth2implementation.security.oauth2.token;

import com.google.common.base.Preconditions;
import com.zeroexception.oauth2implementation.security.oauth2.Constant;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.regex.Matcher;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */


public class SocialAccessTokenImpl implements SocialAccessToken {

    private String token;
    private String provider;

    /**
     * Number 10 needs to be change if the social oauth2 provider name is longer than 10 character.
     * @param preToken
     */

    public SocialAccessTokenImpl(String preToken) {
        String firstPart = preToken.substring(0,10).toLowerCase();
        Matcher matcher = Constant.PROVIDER_PATTERN.matcher(firstPart);
        if (matcher.find()) {
            this.provider = matcher.group(1);
            this.token = preToken.substring(this.provider.length()).trim();
        }
    }

    public SocialAccessTokenImpl(PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken) {
        this(Preconditions.checkNotNull(preAuthenticatedAuthenticationToken).getPrincipal().toString());
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getProvider() {
        return provider;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
