package com.zeroexception.oauth2implementation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        UserDetailsServiceImpl.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return  UserDetailsServiceImpl.userService.findByEmail(s);
    }
}
