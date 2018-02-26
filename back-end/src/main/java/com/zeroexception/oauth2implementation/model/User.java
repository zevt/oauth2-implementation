package com.zeroexception.oauth2implementation.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */


@Entity
@Table(name = "tb_person")
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 50)
    private String firstName;
    @Size(max = 50)
    private String lastName;

    @NotNull
    @Column(unique = true, name = "email")
    private String email;

    @JsonIgnore
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;


    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(
            name = "tb_person_social_id",
            joinColumns = @JoinColumn(
                    name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "social_id", referencedColumnName = "id")
    )
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<SocialId> socialIdSet;


    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(
            name = "tb_person_role",
            joinColumns = @JoinColumn(
                    name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_name", referencedColumnName = "role_name")
    )

    private Set<Role> roles;

    public User() {
        this.password = new BigInteger(130, new SecureRandom()).toString(32);
    }

    public User(String socialIdStr) {
        this();
        this.socialIdSet = new TreeSet<>();
        SocialId socialId = new SocialId(socialIdStr);
        this.socialIdSet.add(socialId);
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", socialIdSet=" + socialIdSet +
                ", roles=" + roles +
                '}';
    }

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public User setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public User addRole(Role role) {
        if (this.roles == null)
            this.roles = new TreeSet<>();
        this.roles.add(role);
        return this;
    }

    public User removeRole(Role role) {
        this.roles.remove(role);
        return this;
    }

    public boolean hasRole(Role role) {
        return this.roles != null && this.roles.contains(role);
    }

    public boolean hasRole(String roleName) {
        Role role = new Role(roleName);
        return this.roles != null && this.roles.contains(role);
    }

    public Set<SocialId> getSocialIdSet() {
        return socialIdSet;
    }

    public User setSocialIdSet(Set<SocialId> socialIdSet) {
        this.socialIdSet = socialIdSet;
        return this;
    }


    public User addSocialId(SocialId socialId) {

        if (this.socialIdSet == null) {
            this.socialIdSet = new TreeSet<>();
        }
        if (!this.socialIdSet.contains(socialId)) {
            this.socialIdSet.add(socialId);
        }
        return this;

    }

    public User addSocialId(String socialIdStr) {
        SocialId socialId = new SocialId(socialIdStr);
        if (this.socialIdSet == null) {
            this.socialIdSet = new TreeSet<>();
        }
        if (!this.socialIdSet.contains(socialId)) {
            this.socialIdSet.add(socialId);
        }
        return this;
    }

    public boolean hasSocialId(SocialId socialId) {
        return this.socialIdSet != null && this.socialIdSet.contains(socialId);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new LinkedList<>();
        roles.forEach(r -> authorities.add(r::getRoleName));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
