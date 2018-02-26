package com.zeroexception.oauth2implementation.services;


import com.zeroexception.oauth2implementation.model.Role;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */


public interface RoleService {

    Role getRole(String roleName);
    void save(Role role);
}
