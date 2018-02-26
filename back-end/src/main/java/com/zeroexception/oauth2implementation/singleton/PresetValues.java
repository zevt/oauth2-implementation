package com.zeroexception.oauth2implementation.singleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Viet Quoc Tran
 * on 8/24/17.
 * www.zeroexception.com
 */

@Component
public class PresetValues {

    public static String FACEBOOK_APP_ID;
    public static String FACEBOOK_APP_SECRET;
    public static String FACEBOOK_APP_TOKEN;
    public static String GOOGLE_CLIENT_ID;
    public static String GOOGLE_CLIENT_SECRET;

    @Autowired
    public PresetValues(@Value("${spring.social.facebook.app-id}") String FACEBOOK_APP_ID,
                        @Value("${spring.social.facebook.app-secret}") String FACEBOOK_APP_SECRET,
                        @Value("${facebook.app-access-token}") String FACEBOOK_APP_TOKEN,
                        @Value("${social.google.0auth2.client-id}") String GOOGLE_CLIENT_ID,
                        @Value("${social.google.0auth2.client-secret}") String GOOGLE_CLIENT_SECRET) {

        PresetValues.FACEBOOK_APP_ID = FACEBOOK_APP_ID;
        PresetValues.FACEBOOK_APP_SECRET = FACEBOOK_APP_SECRET;
        PresetValues.FACEBOOK_APP_TOKEN = FACEBOOK_APP_TOKEN;
        PresetValues.GOOGLE_CLIENT_ID = GOOGLE_CLIENT_ID;
        PresetValues.GOOGLE_CLIENT_SECRET = GOOGLE_CLIENT_SECRET;
    }


    public String getGOOGLE_CLIENT_ID() {
        return GOOGLE_CLIENT_ID;
    }

    public String getGOOGLE_CLIENT_SECRET() {
        return GOOGLE_CLIENT_SECRET;
    }

}
