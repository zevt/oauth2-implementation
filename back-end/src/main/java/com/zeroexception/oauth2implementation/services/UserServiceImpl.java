package com.zeroexception.oauth2implementation.services;

import com.zeroexception.oauth2implementation.model.Role;
import com.zeroexception.oauth2implementation.model.SocialId;
import com.zeroexception.oauth2implementation.model.User;
import com.zeroexception.oauth2implementation.repositories.SocialIdRepository;
import com.zeroexception.oauth2implementation.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */


@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SocialIdRepository socialIdRepo;

    public UserServiceImpl() {
    }

    @Override
    public void addUser(User user) {
        this.userRepository.save(user);
    }


    @Override
    public void addRoleToUser(User user, Role role) {

        if (!user.hasRole(role)) {
            role = this.roleService.getRole(role.getRoleName());
            user.addRole(role);
        }
//        this.userRepo.saveNewTokenTracker(user);
    }

    @Override
    public void addRoleToUser(User user, String roleName) {
        if (!user.hasRole(roleName)) {
            Role role = this.roleService.getRole(roleName);
            user.addRole(role);
        }
    }

    @Override
    public void addRolesToUser(User user, String... roleNames) {
        for (String s : roleNames) {
            this.addRoleToUser(user, s);
        }
    }

    @Override
    @Transactional
    public void removeRoleFromUser(User user, String roleName) {

        Role role = this.roleService.getRole(roleName);
        user.removeRole(role);

    }

    @Override
    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public void saveAllUsers(List<User> userList) {
        userRepository.save(userList);
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }


    @Override
    public List<User> findUserByRole(Role role) {
        return this.userRepository.findUserByRolesContains(role);
    }

    @Override
    public List<User> findUserByRole(String roleName) {
        if (!roleName.startsWith("ROLE_")) {
            roleName = "ROLE_" + roleName;
        }
        Role role = this.roleService.getRole(roleName);
        return this.findUserByRole(role);
    }

    @Override
    public void addSocialIdToUser(User user, String socialIdStr) {
        logger.debug(String.format("Add socialIdStr %s to user %s", socialIdStr, user.getEmail()));
        SocialId socialId = new SocialId(socialIdStr);
        this.socialIdRepo.save(socialId);
        if (!user.hasSocialId(socialId)) {
            user.addSocialId(socialId);
        }

        this.saveUser(user);
    }

    @Override
    public User findBySocialIdStr(String socialIdStr) {
        SocialId socialId = this.socialIdRepo.findBySocialIdFetch(socialIdStr);
        if (socialId == null) {
            return null;
        }
        return socialId.getUser();
    }

}
