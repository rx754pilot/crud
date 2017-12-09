package com.crud.portal.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crud.portal.model.User;
import com.jedlab.framework.spring.dao.AbstractCrudDAO;

public interface UserDao extends AbstractCrudDAO<User>
{

    @Query(value="select u from User u where lower(u.username) = lower(:uname) and u.deletedDate is null")
    User findByUsername(@Param("uname") String username);
    
    @Query(value="select u from User u LEFT join fetch u.roles r where lower(u.username) = lower(:uname) and u.deletedDate is null")
    User findByUsernameWithRoles(@Param("uname") String username);
    
    @Query(value="select u from User u LEFT join fetch u.roles r where u.id = :uid and u.deletedDate is null")
    User findByIdWithRoles(@Param("uid") Long uid);
    

}
