package com.zeroexception.oauth2implementation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */

@Entity
@Table(name = "tb_role")
public class Role implements Comparable {

    public static final String ROLE_BASIC = "ROLE_BASIC";
    public static final String ROLE_PREMIUM = "ROLE_PREMIUM";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Id
    @Column(name = "role_name")
    @JsonProperty("role")
    @Size(max= 50)
    private String roleName;

    @JsonIgnore
    private String description;

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getRoleName() {
        return roleName;
    }

    public Role setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Role setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public int hashCode() {
        return this.roleName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.compareTo(obj) == 0;
    }

    @Override
    public int compareTo(Object obj) {

        if (obj instanceof String) {
            return this.roleName.compareTo(obj.toString());
        }
        if (obj instanceof Role) {
            return this.roleName.compareTo(((Role) obj).roleName);
        }

        throw new IllegalArgumentException(" Cannot compare Role with object typed " + obj.getClass().getName());
    }


}