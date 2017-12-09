package com.crud.portal.service;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.NoResultException;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crud.portal.DateUtil;
import com.crud.portal.dao.LoginActivityDao;
import com.crud.portal.model.LoginActivity;
import com.jedlab.framework.spring.dao.AbstractDAO;
import com.jedlab.framework.spring.service.AbstractCrudService;

@Service
public class LoginActivityService extends AbstractCrudService<LoginActivity>
{

    @Autowired
    LoginActivityDao loginActivityDao;

    @Override
    public AbstractDAO<LoginActivity> getDao()
    {
        return loginActivityDao;
    }

    @Transactional
    public void addActivity(String username, String browser, String ip)
    {
        String token = RandomStringUtils.randomAlphanumeric(15);
        Date ttl = DateUtil.addDate(new Date(), Calendar.MINUTE, 45);
        LoginActivity la = new LoginActivity(username, new Date(), ip, ttl, browser, new Date(), token);
        loginActivityDao.save(la);
    }

    @Transactional
    public void revokeOtherTokens(String userName)
    {
        Date lastUsed = DateUtil.addDate(new Date(), Calendar.MINUTE, -45);
        entityManager.createNamedQuery(LoginActivity.UPDATE_LOGOUT_ALL_SAME_LOGEDIN_USERS).setParameter(1, new Date())
                .setParameter(2, userName).setParameter(3, lastUsed).executeUpdate();
    }

    public Date findLastLogin(String userName)
    {
        try
        {
            return (Date) entityManager.createNamedQuery(LoginActivity.FIND_LAST_ACTIVITY).setParameter(1, userName).setMaxResults(1)
                    .getSingleResult();
        }
        catch (NoResultException e)
        {
            return new Date();
        }
    }

}
