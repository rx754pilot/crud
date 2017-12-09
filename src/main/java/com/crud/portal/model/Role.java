package com.crud.portal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

import com.jedlab.framework.util.StringUtil;

@Table(name = "sec_role")
@Entity
public class Role extends BasePO implements GrantedAuthority
{

    public static final String ACCOUNT_MANAGER = "ROLE_USER";

    public static final String ADMIN = "ROLE_ADMIN";

    @Column(name = "role_name")
    private String name;

    @Column(name = "role_description")
    private String description;

    public Role(Long id)
    {
        setId(id);
    }

    public Role()
    {
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    @Transient
    public String getAuthority()
    {
        return getName();
    }

    @Transient
    public String getTitle()
    {
        if (StringUtil.isEmpty(getDescription()))
            return getName();
        return getDescription();
    }

}
