package com.zeroexception.oauth2implementation.repositories;

import com.zeroexception.oauth2implementation.model.Role;
import com.zeroexception.oauth2implementation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */


public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);
    User findByEmail(String email);
    List<User> findUserByRolesContains(Role role);

}
