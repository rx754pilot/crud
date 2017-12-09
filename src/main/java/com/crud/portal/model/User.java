package com.crud.portal.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.omidbiz.core.axon.internal.IgnoreElement;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jedlab.framework.report.ReportField;
import com.jedlab.framework.spring.dao.PO;
import com.jedlab.framework.spring.security.SecurityUserContext;
import com.jedlab.framework.util.CollectionUtil;
import com.jedlab.framework.util.StringUtil;

@Table(name = "sec_user", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_name" }) })
@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class User extends PO implements UserDetails, SecurityUserContext
{

    @Column(name = "user_name")
    @ReportField(msg = "User_Name")
    private String username;

    @Column(name = "passwd")
    private String passwd;

    @Column(name = "is_enabled")
    private boolean enabled;

    @Column(name = "c_full_name")
    private String fullName;

    @ManyToMany(targetEntity = Role.class)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @NotAudited
    Set<Role> roles = new HashSet<>();

    public User()
    {
    }

    public User(Long userId)
    {
        setId(userId);
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public Set<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return getRoles();
    }

    @Override
    @IgnoreElement
    public String getPassword()
    {
        return passwd;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

    @IgnoreElement
    public String getPasswd()
    {
        return passwd;
    }

    public void setPasswd(String passwd)
    {
        this.passwd = passwd;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    @PrePersist
    public void prePersist()
    {
        setEnabled(true);
    }

    @Transient
    public String getRoleNames()
    {
        if (CollectionUtil.isEmpty(getRoles()))
            return "";
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Role role : getRoles())
        {
            if (i > 0)
                sb.append(", ");
            sb.append(role.getTitle());
            i++;
        }
        return sb.toString();
    }

    @Transient
    public String getRealName()
    {
        if (StringUtil.isEmpty(getFullName()))
            return getUsername();
        return getFullName();
    }

}
