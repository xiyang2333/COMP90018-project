package com.unimelb.studypartner.dao;

import java.io.Serializable;

public class Participant implements Serializable {
    private Integer participantId;

    private Integer userId;

    private Integer meetingId;

    private String participantStatus;

    private static final long serialVersionUID = 1L;

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public String getParticipantStatus() {
        return participantStatus;
    }

    public void setParticipantStatus(String participantStatus) {
        this.participantStatus = participantStatus == null ? null : participantStatus.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", participantId=").append(participantId);
        sb.append(", userId=").append(userId);
        sb.append(", meetingId=").append(meetingId);
        sb.append(", participantStatus=").append(participantStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}