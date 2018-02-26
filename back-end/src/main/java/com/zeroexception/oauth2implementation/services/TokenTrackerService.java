package com.zeroexception.oauth2implementation.services;


import com.zeroexception.oauth2implementation.model.TokenTracker;
import com.zeroexception.oauth2implementation.model.User;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */


public interface TokenTrackerService {

    User findUserByToken(String token);

    TokenTracker findByUserEmail(String email);

    TokenTracker findByToken(String token);

    void saveNewTokenTracker(User user, String token, long expireEpochSecond);

    void update(TokenTracker tokenTracker);

    void deleteAllExpiredToken();

    void deleteAllExpiredTokenBefore(long epochSecond);

    void deleteTokenTrackerByUserEmail(String email);

    void delete(String tokenKey);
}
