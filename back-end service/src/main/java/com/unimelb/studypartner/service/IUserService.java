package com.unimelb.studypartner.service;

import com.unimelb.studypartner.common.CommonException;
import com.unimelb.studypartner.dao.Tag;
import com.unimelb.studypartner.dao.User;
import com.unimelb.studypartner.service.bo.MeetingBO;
import com.unimelb.studypartner.service.bo.MeetingSearchBO;
import com.unimelb.studypartner.service.bo.RegisterBO;

import java.util.List;

/**
 * Created by xiyang on 2019/9/10
 */
public interface IUserService {
    public int loginCheck(String loginName, String password) throws CommonException;

    public List<MeetingBO> searchMeeting(MeetingSearchBO meetingSearchBO) throws CommonException;

    // for demo
    public List<Tag> getAllSearchTag(int userId) throws CommonException;

    public List<Tag> getAllTag() throws CommonException;

    public RegisterBO register(User user, String userPic) throws CommonException;

    public void updateTag(int userId, List<Integer> tags) throws CommonException;
}
