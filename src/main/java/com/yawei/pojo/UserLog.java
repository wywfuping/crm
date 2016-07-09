package com.yawei.pojo;


import java.io.Serializable;

public class UserLog implements Serializable{
    private static final long serialVersionUID = -7286542698766803220L;
    private Integer id;
    private Integer userid;
    private String logintime;
    private String logip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getLogintime() {
        return logintime;
    }

    public void setLogintime(String logintime) {
        this.logintime = logintime;
    }

    public String getLogip() {
        return logip;
    }

    public void setLogip(String logip) {
        this.logip = logip;
    }
}
