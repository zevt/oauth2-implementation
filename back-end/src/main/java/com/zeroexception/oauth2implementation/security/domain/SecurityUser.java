package com.zeroexception.oauth2implementation.security.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zeroexception.oauth2implementation.model.SocialId;
import com.zeroexception.oauth2implementation.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */


public class SecurityUser implements UserDetails {

    private long id;
    private String name;
    private String email;
    @JsonIgnore
    private String password;
    private Set<String> roleSet;
    @JsonIgnore
    private Set<String> socalIdSet;

    public SecurityUser(User user) {
        this.id = user.getId();
        this.name = user.getFirstName() + " " +  user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roleSet = new HashSet<>();
        user.getRoles().forEach( role -> roleSet.add(role.getRoleName()));
        this.socalIdSet = new HashSet<>();
        for (SocialId socialId: user.getSocialIdSet()) {
            this.roleSet.add(socialId.getId());
        }
    }

    @Override
    public String toString() {
        return "SecurityUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", roleSet=" + roleSet +
                ", socalIdSet=" + socalIdSet +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getRoleSet() {
        return roleSet;
    }

    public Set<String> getSocalIdSet() {
        return socalIdSet;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (String role : roleSet) {
            grantedAuthorities.add( () -> role);
        }
        return grantedAuthorities;
    }

    @Override
    @JsonIgnore
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
