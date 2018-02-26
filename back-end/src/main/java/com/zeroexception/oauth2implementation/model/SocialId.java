package com.zeroexception.oauth2implementation.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */


@Entity
@Table(name = "tb_social_id")
public class SocialId implements Comparable {

    @Id
    private String id;
    private String provider;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "person_id")
    private User user;

    public SocialId() {
    }

    public SocialId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SocialId{" +
                "id='" + id + '\'' +
                ", provider='" + provider + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public SocialId setId(String id) {
        this.id = id;
        return this;
    }

    public String getProvider() {
        return provider;
    }

    public SocialId setProvider(String provider) {
        this.provider = provider;
        return this;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SocialId) {
            return this.id.equals(((SocialId) obj).id);
        }
        return false;
    }

    @Override
    public int compareTo(Object obj) {
        if (obj instanceof SocialId) {
            return this.id.compareTo(((SocialId) obj).id);
        }
        throw new IllegalArgumentException(" Incompatible type comparison");
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
