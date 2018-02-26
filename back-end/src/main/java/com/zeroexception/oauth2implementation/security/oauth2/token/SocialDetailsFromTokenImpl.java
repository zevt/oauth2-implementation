package com.zeroexception.oauth2implementation.security.oauth2.token;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */

public class SocialDetailsFromTokenImpl implements SocialDetailsFromToken {
    private String email;
    private String name;
    private String firstName;
    private String lastName;
    private String socialId;
    private long expiresAtEpochSecondl;


    public SocialDetailsFromTokenImpl() {
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    public SocialDetailsFromTokenImpl setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public SocialDetailsFromTokenImpl setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    public SocialDetailsFromTokenImpl setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public SocialDetailsFromTokenImpl setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public SocialDetailsFromTokenImpl setSocialId(String socialId) {
        this.socialId = socialId;
        return this;
    }

    @Override
    public String getId() {
        return this.socialId;
    }

    @Override
    public long expiresAtEpochSecond() {
        return expiresAtEpochSecondl;
    }

    public SocialDetailsFromTokenImpl setExpiresAtEpochSecondl(long expiresAtEpochSecondl) {
        this.expiresAtEpochSecondl = expiresAtEpochSecondl;
        return this;
    }
}
