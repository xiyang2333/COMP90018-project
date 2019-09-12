package com.unimelb.studypartner.web.entity;

import java.util.List;

/**
 * Created by xiyang on 2019/9/11
 */
public class SearchMeetingResponse {
    private int responseStatus;
    private String errorMessage;

    private List<MeetingDetail> meetingDetailList;

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

    public List<MeetingDetail> getMeetingDetailList() {
        return meetingDetailList;
    }

    public void setMeetingDetailList(List<MeetingDetail> meetingDetailList) {
        this.meetingDetailList = meetingDetailList;
    }

}
