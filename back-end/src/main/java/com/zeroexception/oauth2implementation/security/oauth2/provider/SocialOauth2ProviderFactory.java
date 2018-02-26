package com.zeroexception.oauth2implementation.security.oauth2.provider;


import com.zeroexception.oauth2implementation.singleton.PresetValues;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */

public interface SocialOauth2ProviderFactory {

    SocialOauth2Provider FacebookProviderInstance =  new FacebookProvider(PresetValues.FACEBOOK_APP_ID, PresetValues.FACEBOOK_APP_SECRET);
    SocialOauth2Provider GoogleProviderInstance = new GoogleProvider(PresetValues.GOOGLE_CLIENT_ID, PresetValues.GOOGLE_CLIENT_SECRET);
}
