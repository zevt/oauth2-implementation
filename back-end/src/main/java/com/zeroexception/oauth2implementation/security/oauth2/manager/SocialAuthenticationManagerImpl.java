package com.zeroexception.oauth2implementation.security.oauth2.manager;

import com.google.common.base.Preconditions;
import com.zeroexception.oauth2implementation.model.Role;
import com.zeroexception.oauth2implementation.model.User;
import com.zeroexception.oauth2implementation.security.authentication.SocialAuthentication;
import com.zeroexception.oauth2implementation.security.oauth2.Constant;
import com.zeroexception.oauth2implementation.security.oauth2.provider.SocialOauth2Provider;
import com.zeroexception.oauth2implementation.security.oauth2.provider.SocialOauth2ProviderFactory;
import com.zeroexception.oauth2implementation.security.oauth2.token.SocialAccessToken;
import com.zeroexception.oauth2implementation.security.oauth2.token.SocialDetailsFromToken;
import com.zeroexception.oauth2implementation.services.TokenTrackerService;
import com.zeroexception.oauth2implementation.services.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */

public class SocialAuthenticationManagerImpl implements SocialAuthenticationManager, InitializingBean {

    private static Log logger = LogFactory.getLog(SocialAuthenticationManagerImpl.class);

    private UserService userService;
    private TokenTrackerService trackerService;

    public SocialAuthenticationManagerImpl() {
    }

    /**
     * This method takes PreAuthenticatedAuthenticationToken object in
     *
     * @param authentication object of type PreAuthenticatedAuthenticationToken, which contains getPrincipal() to return raw socialToken
     * @return return object of type Authentication.
     * @throws AuthenticationException
     */
    @Override
    @Deprecated
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        logger.debug(" ->   SocialAuthenticationManager executed ");
        return authentication;
    }

    @Override
    public Authentication authenticate(SocialAccessToken socialAccessToken) {
        if (socialAccessToken == null)
            return null;

        String tokenKey = socialAccessToken.getProvider() + "_" + socialAccessToken.getToken();
        User user = this.trackerService.findUserByToken(tokenKey);
        if (user != null) {
            return new SocialAuthentication(user);
        }

        try {
            SocialDetailsFromToken socialDetails;
            SocialOauth2Provider socialOauth2Provider = null;
            if (socialAccessToken.getProvider().equals(Constant.FACEBOOK)) {
                socialOauth2Provider = SocialOauth2ProviderFactory.FacebookProviderInstance;
            } else if (socialAccessToken.getProvider().equals(Constant.GOOGLE)) {
                socialOauth2Provider = SocialOauth2ProviderFactory.GoogleProviderInstance;
            }
            socialDetails = socialOauth2Provider.getSocialDetails(socialAccessToken.getToken());
            if (socialDetails != null) {

                user = this.userService.findBySocialIdStr(socialDetails.getId());
                if (user == null) {
                    user = this.userService.findByEmail(socialDetails.getEmail());
                    if (user != null) {
                        /**
                         * The same email but with different SocialId because
                         * The same user might uses another OAuth2 provider to login.
                         */
                        logger.debug(" Add new Oauth2 Provider to user");
                        this.userService.addSocialIdToUser(user, socialDetails.getId());
                    } else {
                        logger.debug(" New User doesn't exist in database. Create new user");
                        user = new User()
                                .setEmail(socialDetails.getEmail())
                                .addSocialId(socialDetails.getId())
//                                .setName(socialDetails.getName())
                                .setFirstName(socialDetails.getFirstName())
                                .setLastName(socialDetails.getLastName());
                        if (user.getFirstName() == null)
                            user.setFirstName(socialDetails.getName());
                        if (user.getLastName() == null)
                            user.setLastName("");
                        this.userService.addRoleToUser(user, Role.ROLE_BASIC);
                    }

                }
                try {
                    this.trackerService.saveNewTokenTracker(user, tokenKey, socialDetails.expiresAtEpochSecond());
                    logger.debug(" Save token and user info to TokenTracker");
                } catch (Exception e) {
                    logger.debug(" Cannot saveNewTokenTracker ");
                    e.printStackTrace();
                }

                return new SocialAuthentication(user);
            } else return null;
        } catch (Exception e) {
            logger.debug(" Exception At ");
            e.printStackTrace();
            return null;
        }
    }

    public SocialAuthenticationManagerImpl setUserService(UserService userService) {
        this.userService = Preconditions.checkNotNull(userService);
        return this;
    }

    public SocialAuthenticationManagerImpl setTrackerService(TokenTrackerService trackerService) {
        this.trackerService = Preconditions.checkNotNull(trackerService);
        return this;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Preconditions.checkNotNull(this.trackerService);
        Preconditions.checkNotNull(this.userService);
    }
}
