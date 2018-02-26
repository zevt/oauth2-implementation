package com.zeroexception.oauth2implementation.security.oauth2.provider;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.zeroexception.oauth2implementation.security.oauth2.Constant;
import com.zeroexception.oauth2implementation.security.oauth2.token.SocialDetailsFromToken;
import com.zeroexception.oauth2implementation.security.oauth2.token.SocialDetailsFromTokenImpl;
import com.zeroexception.oauth2implementation.singleton.Utilities;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */


public class GoogleProvider implements SocialOauth2Provider {

    private static final Log logger = LogFactory.getLog(GoogleProvider.class);

    private static GoogleIdTokenVerifier TOKEN_VERIFIER;

    /**
     * @see <a href="https://developers.google.com/identity/sign-in/web/backend-auth"></a>
     * Create temporary session code
     * @see <a href="https://developers.google.com/identity/protocols/OpenIDConnect"></a>
     */

    public GoogleProvider(String GOOGLE_CLIENT_ID, String GOOGLE_CLIENT_SECRET) {
        TOKEN_VERIFIER = new GoogleIdTokenVerifier.Builder(Utilities.NET_HTTP_TRANSPORT, JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                .build();


    }

    @Override
    public SocialDetailsFromToken getSocialDetails(String accessToken) {
        try {

            GoogleIdToken idToken = TOKEN_VERIFIER.verify(accessToken);

            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                System.out.println(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
                String userId = payload.getSubject();
                // Get profile information from payload
                String email = payload.getEmail();
                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                System.out.println("emailVerified: " + emailVerified);

                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String locale = (String) payload.get("locale");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");


                return new SocialDetailsFromTokenImpl()
                        .setSocialId(Constant.GOOGLE + "_" + userId)
                        .setName(name).setFirstName(givenName).setLastName(familyName)
                        .setEmail(email)
                        .setExpiresAtEpochSecondl(payload.getExpirationTimeSeconds());
            } else {
                logger.debug("Token is not valid");
                return null;
            }
        } catch ( IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
    }
}
