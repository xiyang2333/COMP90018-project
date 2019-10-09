package com.example.service.entity;

/**
 * Created by xiyang on 2019/10/3
 */
public class UpdateTagResponse {
    private int responseStatus;
    private String errorMessage;

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
