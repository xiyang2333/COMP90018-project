package com.unimelb.studypartner.web.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by xiyang on 2019/10/9
 */
public class AnswerPart {
    private UserPart user;

    private List<String> photoList;
    private String answer;
    private Date answerTime;

    public UserPart getUser() {
        return user;
    }

    public void setUser(UserPart user) {
        this.user = user;
    }

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }
}
