package com.yawei.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class SalesLog implements Serializable{
    private static final long serialVersionUID = -6776129319579505784L;
    private Integer id;
    private Integer salesid;
    private String context;
    private Timestamp createtime;
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSalesid() {
        return salesid;
    }

    public void setSalesid(Integer salesid) {
        this.salesid = salesid;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
