package com.yawei.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JSONResult {
    public static final String SUCCESS="success";
    public static final String ERROR="error";
    private String state;
    private String message;
    private Object data;

    //定义传message为错误状态
    public JSONResult(String message) {
        this(ERROR,message);
    }

    //定义传data为成功状态
    public JSONResult(Object data) {
        this(SUCCESS,data);
    }

    public JSONResult(String state, Object data) {
        this.state = state;
        this.data = data;
    }

    public JSONResult(String state, String message) {
        this.state = state;
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
