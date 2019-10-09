package com.unimelb.studypartner.web.entity;

/**
 * Created by xiyang on 2019/10/3
 */
public class JoinActivityResponse {
    private int responseStatus;
    private String errorMessage;

    private int participantId;

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

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }
}
