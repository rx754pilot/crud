package com.crud.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.crud.portal.dao.UserDao;
import com.crud.portal.model.User;

public class UserDetailService implements UserDetailsService
{

    
    @Autowired
    UserDao userDao;
    
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userDao.findByUsernameWithRoles(username);
        if(user == null)
            throw new UsernameNotFoundException("invalid username");
        user.getAuthorities();
        return user;
    }

}
