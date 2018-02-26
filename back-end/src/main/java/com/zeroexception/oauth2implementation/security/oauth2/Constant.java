package com.zeroexception.oauth2implementation.security.oauth2;

import java.util.regex.Pattern;

/**
 * @author Viet Quoc Tran
 * on 8/26/17.
 * www.zeroexception.com
 */

public interface Constant {
    String FACEBOOK = "facebook";
    String GOOGLE = "google";
    String LINKEDIN = "linkedin";
    String ACCESS_TOKEN = "access_token";
    String TOKEN_TRACKER_KEY = "token_tracker_key";
    Pattern PROVIDER_PATTERN = Pattern.compile("((facebook)|(google)|(linkedin))(.*)");
}
