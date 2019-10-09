package com.unimelb.studypartner.common;

/**
 * Created by xiyang on 2019/9/10
 */
public class CommonException extends Exception {
    private String warnMessage;
    private int returnStatus;

    public CommonException(String warnMessage, int returnStatus) {
        this.warnMessage = warnMessage;
        this.returnStatus = returnStatus;
    }

    public CommonException() {
    }

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
