package com.zeroexception.oauth2implementation.restApiController.securityApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zeroexception.oauth2implementation.model.TokenTracker;
import com.zeroexception.oauth2implementation.model.User;
import com.zeroexception.oauth2implementation.security.authentication.OAuthResponse;
import com.zeroexception.oauth2implementation.security.domain.SecurityUser;
import com.zeroexception.oauth2implementation.security.oauth2.Constant;
import com.zeroexception.oauth2implementation.services.TokenTrackerService;
import com.zeroexception.oauth2implementation.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import org.springframework.transaction.annotation.Transactional;


/**
 * @author Viet Quoc Tran
 * on 9/8/17.
 * www.zeroexception.com
 */


@RestController
@RequestMapping("/oauth2")
public class RegistrationApi {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationApi.class);

    private TokenTrackerService trackerService;
    private UserService userService;
//    private PersonalWordService wordService;

    @Autowired
    public RegistrationApi(TokenTrackerService trackerService, UserService userService) {
        this.trackerService = trackerService;
        this.userService = userService;
    }

    @GetMapping("/sign-up")
//    @Transactional
    public ResponseEntity<?> signUp(@RequestAttribute(Constant.TOKEN_TRACKER_KEY) String tokenKey) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        if (tokenKey == null || tokenKey.isEmpty()) {
            node.put("message", "failed");
            node.put("reason", "invalid_token");
            return  new ResponseEntity<>(node, HttpStatus.OK);
        }

        try {
            User securityUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = this.userService.findByEmail(securityUser.getEmail());
            if (user == null) {
                try {
                    this.userService.saveUser(securityUser);
                } catch (Exception e) {
                    node.put("message", "failed");
                    node.put("reason", "fail to register");
                    return new ResponseEntity<>(node, HttpStatus.OK);
                }
                TokenTracker tokenTracker = this.trackerService.findByToken(tokenKey);

                user = this.userService.findByEmail(securityUser.getEmail());
                OAuthResponse authResponse = new OAuthResponse()
                        .setExpiresAt(tokenTracker.getExpireAtEs())
                        .setUser(new SecurityUser(user));

                ObjectMapper mapper = new ObjectMapper();
                node.put("auth", mapper.writeValueAsString(authResponse));
                node.put("message", "success");
                return new ResponseEntity<>(node, HttpStatus.ACCEPTED);
//                return new ResponseEntity<>("  WTF", HttpStatus.ACCEPTED);
            } else {
                node.put("message", "fail");
                node.put("reason", "user_already_existed");
                return new ResponseEntity<>(node, HttpStatus.OK);
            }
        } catch (Exception e) {
            node.put("message", "failed");
            node.put("reason", "unknown reason");
            return new ResponseEntity<>(node, HttpStatus.OK);
        }
    }


    @GetMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestAttribute(Constant.TOKEN_TRACKER_KEY) String tokenKey) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        try {
            User securityUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            logger.debug(securityUser.toString());
            User user = this.userService.findByEmail(securityUser.getEmail());
            if (user != null) {
                TokenTracker tokenTracker = this.trackerService.findByToken(tokenKey);
                OAuthResponse authResponse = new OAuthResponse();
                authResponse.setExpiresAt(tokenTracker.getExpireAtEs());
                authResponse.setUser(new SecurityUser(user));
                ObjectMapper mapper = new ObjectMapper();
                node.put("auth", mapper.writeValueAsString(authResponse));
                node.put("message", "success");
                return new ResponseEntity<>(node, HttpStatus.ACCEPTED);
            } else {
                node.put("message", "fail");
                node.put("reason", "user_not_found");
                return new ResponseEntity<>(node, HttpStatus.OK);
            }
        } catch (Exception e) {
            node.put("message", "fail");
            node.put("reason", "invalid_token");
            return new ResponseEntity<>(node, HttpStatus.OK);
        }
    }


    @GetMapping("/sign-out")
    public ResponseEntity<?> signOut(@RequestAttribute(Constant.TOKEN_TRACKER_KEY) String tokenKey) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        try {
            this.trackerService.delete(tokenKey);
            node.put("message", "success");
            return new ResponseEntity<>(node, HttpStatus.OK);
        } catch (Exception e) {
            node.put("message", "fail");
            return new ResponseEntity<>(node, HttpStatus.OK);
        }

    }


    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestAttribute(Constant.TOKEN_TRACKER_KEY) String tokenKey) {
        User securityUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TokenTracker tokenTracker = this.trackerService.findByToken(tokenKey);
        OAuthResponse authResponse = new OAuthResponse()
                .setExpiresAt(tokenTracker.getExpireAtEs())
                .setUser(new SecurityUser(securityUser));

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

}
