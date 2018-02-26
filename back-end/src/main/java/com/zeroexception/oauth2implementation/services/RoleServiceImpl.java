package com.zeroexception.oauth2implementation.services;

import com.zeroexception.oauth2implementation.model.Role;
import com.zeroexception.oauth2implementation.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRole(String roleName) {
        Role role = this.roleRepository.queryByRoleName(roleName);
        if (role == null) {
            role = new Role(roleName);
            this.roleRepository.saveAndFlush(role);
        }
        return role;
    }

    @Override
    public void save(Role role) {
        this.roleRepository.saveAndFlush(role);
    }
}
