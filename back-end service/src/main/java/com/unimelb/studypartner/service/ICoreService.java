package com.unimelb.studypartner.service;

import com.unimelb.studypartner.dao.CommonException;
import com.unimelb.studypartner.dao.Tag;
import com.unimelb.studypartner.service.bo.MeetingBO;
import com.unimelb.studypartner.service.bo.MeetingSearchBO;

import java.util.List;

/**
 * Created by xiyang on 2019/9/10
 */
public interface ICoreService {
    public int loginCheck(String loginName, String password) throws CommonException;

    public List<MeetingBO> searchMeeting(MeetingSearchBO meetingSearchBO) throws CommonException;

    // for demo
    public List<Tag> getAllSearchTag(int userId) throws CommonException;

    public List<Tag> getAllTag() throws CommonException;
}
