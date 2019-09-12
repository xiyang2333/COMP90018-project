package com.unimelb.studypartner.dao;

/**
 * Created by xiyang on 2019/9/10
 */
public class CommonException extends Exception {
    private String warnMessage;
    private int returnStatus;

    public String getWarnMessage() {
        return warnMessage;
    }

    public void setWarnMessage(String warnMessage) {
        this.warnMessage = warnMessage;
    }

    public int getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(int returnStatus) {
        this.returnStatus = returnStatus;
    }
}
