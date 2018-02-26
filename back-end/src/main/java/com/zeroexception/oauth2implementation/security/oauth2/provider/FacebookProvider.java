package com.zeroexception.oauth2implementation.security.oauth2.provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeroexception.oauth2implementation.security.oauth2.Constant;
import com.zeroexception.oauth2implementation.security.oauth2.token.SocialDetailsFromToken;
import com.zeroexception.oauth2implementation.security.oauth2.token.SocialDetailsFromTokenImpl;
import com.zeroexception.oauth2implementation.singleton.PresetValues;
import com.zeroexception.oauth2implementation.singleton.Utilities;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;

import java.io.IOException;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */

/**
 * For reference, please check the following links
 * @see <a href="https://developers.facebook.com/docs/facebook-login/permissions"></a>
 * @see <a href="https://developers.facebook.com/docs/facebook-login/manually-build-a-login-flow"></a>
 * @see <a href="https://developers.facebook.com/docs/facebook-login/access-tokens/#apptokens"></a>
 */

public class FacebookProvider implements SocialOauth2Provider, InitializingBean {

    private static final Log logger = LogFactory.getLog(FacebookProvider.class);
    private static String scope = "public_profile,email";
    private static FacebookConnectionFactory factory;
    private static String identityUrl = "https://graph.facebook.com/me?fields=id,name,email";
    private static String verificationUrl = "https://graph.facebook.com/debug_token?input_token=";

    @Value("${facebook.app-access-token}")
    private String facebookAppToken;

    public FacebookProvider(String FACEBOOK_APP_ID, String FACEBOOK_APP_SECRET) {
        factory = new FacebookConnectionFactory(FACEBOOK_APP_ID, FACEBOOK_APP_SECRET);
    }

    @Override
    public SocialDetailsFromToken getSocialDetails(String accessToken) {

        AccessGrant accessGrant = new AccessGrant(accessToken, scope, "", 100L);

        Facebook facebook;
        try {
            Connection<Facebook> facebookConnection = factory.createConnection(accessGrant);
            facebook = facebookConnection.getApi();
        } catch (Exception  e) {
            logger.debug(e.getMessage());
//            e.printStackTrace();
            return null;
        }
        logger.debug(this.facebookAppToken);
        String url = verificationUrl + accessToken + "&access_token=" + PresetValues.FACEBOOK_APP_TOKEN;

        ResponseEntity<String> vefiResponse = facebook.restOperations().exchange(url , HttpMethod.GET, Utilities.getInstance().getHttpEntityString(), String.class);
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode vefiRoot = mapper.readTree(vefiResponse.getBody());
            System.out.println(vefiRoot);
            JsonNode veriNode = vefiRoot.path("data");
            if (!Boolean.parseBoolean(veriNode.path("is_valid").asText()))
                return null;

            ResponseEntity<String> identityResponse = facebook.restOperations().exchange(identityUrl, HttpMethod.GET, Utilities.getInstance().getHttpEntityString(), String.class);
            JsonNode identityRoot = mapper.readTree(identityResponse.getBody());
            System.out.println(identityRoot);
            String email = identityRoot.path("email").asText();

            long expires_at  =  Long.parseLong(veriNode.path("expires_at").asText());
            return new SocialDetailsFromTokenImpl()
                    .setSocialId(Constant.FACEBOOK + "_" + identityRoot.path("id").asText())
                    .setName(identityRoot.path("name").asText())
                    .setEmail(email)
                    .setExpiresAtEpochSecondl(expires_at);

        } catch (IOException e) {
            logger.debug(e);
            return null;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}