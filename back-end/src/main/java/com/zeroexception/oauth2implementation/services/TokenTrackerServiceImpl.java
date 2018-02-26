package com.zeroexception.oauth2implementation.services;

import com.google.common.base.Preconditions;
import com.zeroexception.oauth2implementation.model.TokenTracker;
import com.zeroexception.oauth2implementation.model.User;
import com.zeroexception.oauth2implementation.repositories.TokenTrackerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */


@Service
public class TokenTrackerServiceImpl implements TokenTrackerService {
    private static Logger looger = LoggerFactory.getLogger(TokenTrackerServiceImpl.class);
    private TokenTrackerRepository tokenTrackerRepo;

    @Autowired
    public TokenTrackerServiceImpl(TokenTrackerRepository tokenTrackerRepository) {
        this.tokenTrackerRepo = tokenTrackerRepository;
    }

    @Override
    public User findUserByToken(String token) {
        TokenTracker tokenTracker = this.tokenTrackerRepo.findOneByToken(token);
        return tokenTracker != null ? tokenTracker.getUser() : null;
    }

    @Override
    public TokenTracker findByUserEmail(String email) {
        return this.tokenTrackerRepo.findOneByUserEmail(email);
    }

    @Override
    public TokenTracker findByToken(String token) {
        return this.tokenTrackerRepo.findOneByToken(token);
    }

    @Override
    public void saveNewTokenTracker(User user, String token, long expireEpochSecond) {
        looger.debug(" Save TokenTracker ");
        token = Preconditions.checkNotNull(token, " Key token cannot be null ");
        Preconditions.checkNotNull(user);
        TokenTracker tokenTracker = new TokenTracker()
                .setAtomicId()
                .setToken(token)
                .setUser(user)
                .setExpireAtEs(expireEpochSecond);
        this.tokenTrackerRepo.save(tokenTracker);
    }

    @Override
    public void update(TokenTracker tokenTracker) {
        this.tokenTrackerRepo.save(tokenTracker);
    }

    @Override
    public void deleteAllExpiredToken() {
        this.tokenTrackerRepo.deleteAllByExpireAtEsBefore(Instant.now().getEpochSecond() - 600);
    }

    @Override
    public void deleteAllExpiredTokenBefore(long epochSecond) {
        this.tokenTrackerRepo.deleteAllByExpireAtEsBefore(epochSecond);
    }

    @Override
    public void deleteTokenTrackerByUserEmail(String email) {
        this.tokenTrackerRepo.deleteTokenTrackerByUserEmail(email);
    }

    @Override
    public void delete(String tokenKey) {
        this.tokenTrackerRepo.deleteTokenTrackerByToken(tokenKey);
    }
}
