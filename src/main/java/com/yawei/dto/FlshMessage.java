package com.yawei.dto;


public class FlshMessage {
    public static final String STATE_SUCCESS = "success";
    public static final String STATE_ERROR = "error";

    private String state;
    private String message;

    public FlshMessage(String message) {
        this.state = STATE_SUCCESS;
        this.message = message;
    }

    public FlshMessage(String state, String message) {
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
}
