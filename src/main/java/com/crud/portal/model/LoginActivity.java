package com.crud.portal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.jedlab.framework.util.StringUtil;

@NamedQueries({
        @NamedQuery(name = LoginActivity.FIND_LOGIN_ACTIVITY_BY_USER_NAME, query = "select la from LoginActivity la where la.username = ? and la.token is not null"),
        @NamedQuery(name = LoginActivity.FIND_LAST_ACTIVITY, query = "select la.lastUsed from LoginActivity la where la.username = ? and la.token is null order by la.createdAt desc"),
        @NamedQuery(name = LoginActivity.UPDATE_LAST_USED, query = "update LoginActivity la set lastUsed = ? where la.token = ?"),
        @NamedQuery(name = LoginActivity.UPDATE_LOGOUT_ALL_SAME_LOGEDIN_USERS, query = "update LoginActivity la set la.token = null, la.ttl = ? where la.token is not null AND (la.username = ? OR la.lastUsed <= ?)"),
        @NamedQuery(name = LoginActivity.ONLINE_USERS, query = "select count(*) from LoginActivity la where la.token is not null"),
        @NamedQuery(name = LoginActivity.FETCH_ACTIVE_USERS, query = "select la from LoginActivity la where la.token is not null and la.lastUsed >= ?") })
@Entity
@Table(name = "login_activity")
public class LoginActivity extends BasePO
{

    public static final String TOKEN = "token";
    public static final String FIND_LOGIN_ACTIVITY_BY_USER_NAME = "users.findLoginActivityByUserName";
    public static final String UPDATE_LOGOUT_ALL_SAME_LOGEDIN_USERS = "users.updateLogoutAllSameLogedInUsers";
    public static final String FETCH_ACTIVE_USERS = "users.fetchActiveUsers";
    public static final String UPDATE_LAST_USED = "users.updateLastUsed";
    public static final String ONLINE_USERS = "users.onlineUsers";
    public static final String FIND_LAST_ACTIVITY = "users.findLastActivity";

    private String username;
    private Date lastUsed;
    private String ipAddress;
    private Date ttl;
    private String device;
    private Date createdAt;
    private String token;

    public LoginActivity()
    {
    }

    public LoginActivity(String username, Date lastUsed, String ipAddress, Date ttl, String device, Date createdAt, String token)
    {
        this.username = username;
        this.lastUsed = lastUsed;
        this.ipAddress = ipAddress;
        this.ttl = ttl;
        this.device = device;
        this.createdAt = createdAt;
        this.token = token;
    }

    @Column(name = "username")
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    @Column(name = "last_used")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastUsed()
    {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed)
    {
        this.lastUsed = lastUsed;
    }

    @Column(name = "ip_address")
    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    @Column(name = "ttl")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getTtl()
    {
        return ttl;
    }

    public void setTtl(Date ttl)
    {
        this.ttl = ttl;
    }

    @Column(name = "device")
    public String getDevice()
    {
        return device;
    }

    public void setDevice(String device)
    {
        this.device = device;
    }

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    @Column(name = "token")
    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    @Transient
    public boolean isFailedLogin()
    {
        return StringUtil.isEmpty(getToken()) && getLastUsed() == null;
    }

}