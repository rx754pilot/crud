package com.crud.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

import com.crud.portal.dao.UserDao;
import com.crud.portal.model.User;
import com.jedlab.framework.spring.security.AuthenticationUtil;

public class AuditAwareImpl implements AuditorAware<User>
{

    
    @Autowired
    UserDao userDao;
    
    @Override
    public User getCurrentAuditor()
    {
        if(AuthenticationUtil.getUserId() == null)
            return null;
        return userDao.findOne(AuthenticationUtil.getUserId());
    }

}
