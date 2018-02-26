package com.zeroexception.oauth2implementation.services;

import com.zeroexception.oauth2implementation.model.Role;
import com.zeroexception.oauth2implementation.model.User;

import java.util.List;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */

public interface UserService {

    void addUser(User user);

    void addRoleToUser(User user, Role role);

    void addRoleToUser(User user, String roleName);

    void addRolesToUser(User user, String... roleNames);

    void removeRoleFromUser(User user, String roleName);

    void saveUser(User user);

    void saveAllUsers(List<User> userList);

    User findByEmail(String email);

    List<User> findUserByRole(Role role);

    List<User> findUserByRole(String roleName);


    User findBySocialIdStr(String socialIdStr);

    void addSocialIdToUser(User user, String socialIdStr);

}
