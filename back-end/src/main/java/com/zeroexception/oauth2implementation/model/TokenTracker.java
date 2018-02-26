package com.zeroexception.oauth2implementation.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */



@Document
@TypeAlias("TokenTracker")
public class TokenTracker {
    
    private static Logger logger = LoggerFactory.getLogger(TokenTracker.class);
    
    private static AtomicLong idCounter = new AtomicLong(0);
    
    @Id
    private long id;
    
    @Indexed
    private long expireAtEs;
    
    @TextIndexed
    private String token; 
    private User user;

    public TokenTracker() {
    }

    public long getId() {
        return id;
    }

    public TokenTracker setId(long id) {
        this.id = id;
        return this;
    }

    /**
     *
     * @return the time when the token expires in EpochSecond
     */
    public long getExpireAtEs() {
        return expireAtEs;
    }

    public TokenTracker setExpireAtEs(long expireAtEs) {
        this.expireAtEs = expireAtEs;
        return this;
    }

    public String getToken() {
        return token;
    }

    public TokenTracker setToken(String token) {
        this.token = token;
        return this;
    }

    public User getUser() {
        return user;
    }

    public TokenTracker setUser(User user) {
        this.user = user;
        return this;
    }

    public TokenTracker setAtomicId() {
        this.id = generateId();
        return this;
    }

    public static long generateId() {
        return idCounter.incrementAndGet();
    }


}
