package com.yawei.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class User implements Serializable{

    private static final long serialVersionUID = -2949882892124045386L;
    private Integer id;
    private String username;
    private String password;
    private String realname;
    private String weixin;
    private Timestamp createtime;
    private Integer roleid;
    private Role role;
    private Boolean enable;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreattime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
