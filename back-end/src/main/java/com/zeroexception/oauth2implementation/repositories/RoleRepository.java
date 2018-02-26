package com.zeroexception.oauth2implementation.repositories;

import com.zeroexception.oauth2implementation.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Viet Quoc Tran
 * www.mylingbook.com
 */


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role queryByRoleName(String roleName);

}
