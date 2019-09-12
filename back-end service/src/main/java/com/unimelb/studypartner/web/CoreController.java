package com.unimelb.studypartner.web;

import com.unimelb.studypartner.dao.CommonException;
import com.unimelb.studypartner.dao.Tag;
import com.unimelb.studypartner.service.ICoreService;
import com.unimelb.studypartner.service.bo.MeetingBO;
import com.unimelb.studypartner.service.bo.MeetingSearchBO;
import com.unimelb.studypartner.web.entity.*;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiyang on 2019/9/4
 */
@RestController
@RequestMapping("/studypartner")
public class CoreController {
    private static Logger logger = Logger.getLogger(CoreController.class);

    private static final String SALT = "STUDY_PARTNER";
    private static final int SAFETY_CHECK_STATUS = -999;

    @Autowired
    private ICoreService coreService;

    @RequestMapping(value = "/logincheck", method = RequestMethod.POST)
    public LoginCheckResponse loginCheck(@RequestBody LoginCheckRequest request){
        LoginCheckResponse response= new LoginCheckResponse();

        try{
            RequestHeader header = request.getRequestHeader();
            // safety checks
            if(header == null || !referenceNoCheck(header.getReferenceNo(), header.getSignature())){
                response.setResponseStatus(SAFETY_CHECK_STATUS);
                response.setErrorMessage("safety checking failure");
            } else {
                //get user id
                int userId = coreService.loginCheck(request.getLoginName(), request.getPassword());
                //set response
                response.setUserId(userId);
                response.setResponseStatus(1);
            }

        } catch (CommonException ex){
            logger.error(ex.getWarnMessage());

            response.setResponseStatus(ex.getReturnStatus());
            response.setErrorMessage(ex.getWarnMessage());
        }

        return response;
    }

    @RequestMapping(value = "/searchmeeting", method = RequestMethod.POST)
    public SearchMeetingResponse searchMeeting(@RequestBody SearchMeetingRequest request){
        SearchMeetingResponse response = new SearchMeetingResponse();
        try{
            RequestHeader header = request.getRequestHeader();
            MeetingSearchBO meetingSearchBO = new MeetingSearchBO();
            BeanUtils.copyProperties(request, meetingSearchBO);
            //get meetingList
            List<MeetingBO> meetingList = coreService.searchMeeting(meetingSearchBO);

            //copy value & set response
            List<MeetingDetail> meetingDetails = new ArrayList<>();
            if(meetingList != null) {
                for (MeetingBO bo : meetingList) {
                    MeetingDetail detail = new MeetingDetail();
                    BeanUtils.copyProperties(bo, detail);
                    meetingDetails.add(detail);
                }
            }
            response.setMeetingDetailList(meetingDetails);
            response.setResponseStatus(1);

        } catch (CommonException ex){
            logger.error(ex.getWarnMessage());

            response.setResponseStatus(ex.getReturnStatus());
            response.setErrorMessage(ex.getWarnMessage());
        }
        return response;
    }

    // demo
    @RequestMapping(value = "/taglist", method = RequestMethod.POST)
    public List<Tag> tagListget(@RequestBody int userId){
        try {
            return coreService.getAllSearchTag(userId);
        } catch (CommonException e) {
            logger.error(e.getWarnMessage());
        }

        return null;
    }

    // check reference no
    private static boolean referenceNoCheck(String referenceNo, String signature){
        String base = referenceNo + SALT;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());

        return md5.equals(signature);
    }
}
