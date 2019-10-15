package com.unimelb.studypartner.web;

import com.unimelb.studypartner.common.CommonException;
import com.unimelb.studypartner.dao.Activity;
import com.unimelb.studypartner.dao.Post;
import com.unimelb.studypartner.dao.Tag;
import com.unimelb.studypartner.dao.User;
import com.unimelb.studypartner.service.IMainPageService;
import com.unimelb.studypartner.service.IUserService;
import com.unimelb.studypartner.service.bo.*;
import com.unimelb.studypartner.web.entity.*;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IUserService userService;

    @Autowired
    private IMainPageService mainPageService;

    @RequestMapping(value = "/logincheck", method = RequestMethod.POST)
    public LoginCheckResponse loginCheck(@RequestBody LoginCheckRequest request) {
        LoginCheckResponse response = new LoginCheckResponse();

        try {
//            RequestHeader header = request.getRequestHeader();
            //get user id
            int userId = userService.loginCheck(request.getUserLoginName(), request.getUserPassword());
            //set response
            response.setUserId(userId);
            response.setResponseStatus(1);

        } catch (CommonException ex) {
            logger.error(ex.getWarnMessage());

            response.setResponseStatus(ex.getReturnStatus());
            response.setErrorMessage(ex.getWarnMessage());
        }

        return response;
    }

//    @RequestMapping(value = "/searchmeeting", method = RequestMethod.POST)
//    public SearchMeetingResponse searchMeeting(@RequestBody SearchMeetingRequest request) {
//        SearchMeetingResponse response = new SearchMeetingResponse();
//        try {
//            RequestHeader header = request.getRequestHeader();
//            MeetingSearchBO meetingSearchBO = new MeetingSearchBO();
//            BeanUtils.copyProperties(request, meetingSearchBO);
//            //get meetingList
//            List<MeetingBO> meetingList = userService.searchMeeting(meetingSearchBO);
//
//            //copy value & set response
//            List<MeetingDetail> meetingDetails = new ArrayList<>();
//            if (meetingList != null) {
//                for (MeetingBO bo : meetingList) {
//                    MeetingDetail detail = new MeetingDetail();
//                    BeanUtils.copyProperties(bo, detail);
//                    meetingDetails.add(detail);
//                }
//            }
//            response.setMeetingDetailList(meetingDetails);
//            response.setResponseStatus(1);
//
//        } catch (CommonException ex) {
//            logger.error(ex.getWarnMessage());
//
//            response.setResponseStatus(ex.getReturnStatus());
//            response.setErrorMessage(ex.getWarnMessage());
//        }
//        return response;
//    }

    // demo
    @RequestMapping(value = "/taglist", method = RequestMethod.POST)
    public List<Tag> tagListget(@RequestBody int userId) {
        try {
            return userService.getAllSearchTag(userId);
        } catch (CommonException e) {
            logger.error(e.getWarnMessage());
        }

        return null;
    }

    @RequestMapping(value = "/alltag", method = RequestMethod.GET)
    public TagListResponse allTagGet() {
        TagListResponse tagListResponse = new TagListResponse();
        try {
            List<Tag> tags =  userService.getAllTag();
            tagListResponse.setTagList(tags);
            tagListResponse.setResponseStatus(0);
        } catch (CommonException e) {
            logger.error(e.getWarnMessage());
            tagListResponse.setResponseStatus(-1);
            tagListResponse.setErrorMessage(e.getWarnMessage());
        }

        return tagListResponse;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        RegisterResponse response = new RegisterResponse();
        try {
            User user = new User();
            BeanUtils.copyProperties(request, user);
            RegisterBO registerBO = userService.register(user, request.getUserPic());
            BeanUtils.copyProperties(registerBO, response);
            response.setResponseStatus(0);
        } catch (CommonException ex) {
            logger.error(ex.getWarnMessage());

            response.setResponseStatus(ex.getReturnStatus());
            response.setErrorMessage(ex.getWarnMessage());
        }

        return response;
    }

    @RequestMapping(value = "/updatetag", method = RequestMethod.POST)
    public UpdateTagResponse updateTag(@RequestBody UpdateTagRequest request) {
        UpdateTagResponse response = new UpdateTagResponse();
        try {
            userService.updateTag(request.getUserId(), request.getUserTags());
            response.setResponseStatus(0);
        } catch (CommonException ex) {
            logger.error(ex.getWarnMessage());

            response.setResponseStatus(ex.getReturnStatus());
            response.setErrorMessage(ex.getWarnMessage());
        }
        return response;
    }

    @RequestMapping(value = "/joinactivity", method = RequestMethod.POST)
    public JoinActivityResponse joinActivity(@RequestBody JoinActivityRequest request) {
        JoinActivityResponse response = new JoinActivityResponse();
        try {
            int parrticipantId = mainPageService.joinActivity(request.getUserId(), request.getActivityId(), request.getType(), request.getParticipantId());
            response.setParticipantId(parrticipantId);
        } catch (CommonException ex) {
            logger.error(ex.getWarnMessage());

            response.setResponseStatus(ex.getReturnStatus());
            response.setErrorMessage(ex.getWarnMessage());
        }
        return response;
    }

    @RequestMapping(value = "/answerpost", method = RequestMethod.POST)
    public AnswerPostResponse answerPost(@RequestBody AnswerPostRequest request) {
        AnswerPostResponse response = new AnswerPostResponse();
        try {
            mainPageService.answerQuestion(request.getUserId(), request.getPostId(), request.getAnswer(), request.getPhotoList());
        } catch (CommonException ex) {
            logger.error(ex.getWarnMessage());

            response.setResponseStatus(ex.getReturnStatus());
            response.setErrorMessage(ex.getWarnMessage());
        }
        return response;
    }

    @RequestMapping(value = "/useractivity", method = RequestMethod.POST)
    public UserActivityResponse userActivity(@RequestBody UserActivityRequest request){
        UserActivityResponse response = new UserActivityResponse();
        try {
            int pageSize = request.getPageSize() == 0 ? 10 : request.getPageSize();
            int pageNumber = request.getPageNumber() == 0 ? 1 : request.getPageNumber();
            List<Activity> activities = new ArrayList<>();
            // type == 0 || 1 means user create activity
            // type == 2 user join activity
            if(request.getType() == 0 || request.getType() == 1) {
                activities = mainPageService.getUserActivity(request.getUserId(), pageSize, pageNumber);
            } else if (request.getType() == 2){
                activities = mainPageService.getUserJoinActivity(request.getUserId(), pageSize, pageNumber);
            }
            List<ActivityPart> partList = new ArrayList<>();
            for(Activity activity: activities){
                ActivityPart activityPart = new ActivityPart();
                BeanUtils.copyProperties(activity, activityPart);
                partList.add(activityPart);
            }
            response.setResponseStatus(0);
            response.setActivityPartList(partList);
        } catch (CommonException ex) {
            logger.error(ex.getWarnMessage());

            response.setResponseStatus(ex.getReturnStatus());
            response.setErrorMessage(ex.getWarnMessage());
        }
        return response;
    }

    @RequestMapping(value = "/userpost", method = RequestMethod.POST)
    public UserPostResponse userPost(@RequestBody UserPostRequest request){
        UserPostResponse response = new UserPostResponse();
        try {
            int pageSize = request.getPageSize() == 0 ? 10 : request.getPageSize();
            int pageNumber = request.getPageNumber() == 0 ? 1 : request.getPageNumber();
            List<Post> posts = mainPageService.getUserPost(request.getUserId(), pageSize, pageNumber);
            List<PostPart> partList = new ArrayList<>();
            for(Post post: posts){
                PostPart postPart = new PostPart();
                BeanUtils.copyProperties(post, postPart);
                partList.add(postPart);
            }
            response.setResponseStatus(0);
            response.setPostList(partList);
        } catch (CommonException ex) {
            logger.error(ex.getWarnMessage());

            response.setResponseStatus(ex.getReturnStatus());
            response.setErrorMessage(ex.getWarnMessage());
        }
        return response;
    }

    @RequestMapping(value = "/createactivity", method = RequestMethod.POST)
    public CreateActivityResponse createActivity(@RequestBody CreateActivityRequest request){
        CreateActivityResponse response = new CreateActivityResponse();
        try{
            ActivityBO activityBO = new ActivityBO();
            BeanUtils.copyProperties(request, activityBO);
            int activityId = mainPageService.CreateActivity(activityBO, request.getUserList());

            response.setActivityId(activityId);
            response.setResponseStatus(0);
        } catch (CommonException ex){
            logger.error(ex.getWarnMessage());

            response.setResponseStatus(ex.getReturnStatus());
            response.setErrorMessage(ex.getWarnMessage());
        }
        return response;
    }

    @RequestMapping(value = "/getactivity", method = RequestMethod.POST)
    public GetActivityResponse getActivity(@RequestBody GetActivityRequest request){
        GetActivityResponse response = new GetActivityResponse();
        try{
            ActivityBO activityBO = mainPageService.getActivity(request.getActivityId(), request.getUserId());
            BeanUtils.copyProperties(activityBO, response);
            if(activityBO.getCreateUser() != null){
                UserPart userPart = new UserPart();
                BeanUtils.copyProperties(activityBO.getCreateUser(), userPart);
                response.setCreateUser(userPart);
            }
            if(activityBO.getUserBOList() != null && activityBO.getUserBOList().size() > 0){
                List<UserPart> userParts = new ArrayList<>();
                for(UserBO userBO : activityBO.getUserBOList()){
                    UserPart userPart = new UserPart();
                    BeanUtils.copyProperties(userBO, userPart);

                    userParts.add(userPart);
                }
                response.setUserList(userParts);
            }
            response.setResponseStatus(0);
        } catch (CommonException ex){
            logger.error(ex.getWarnMessage());

            response.setResponseStatus(ex.getReturnStatus());
            response.setErrorMessage(ex.getWarnMessage());
        }
        return response;
    }

    @RequestMapping(value = "/createpost", method = RequestMethod.POST)
    public CreatePostResponse createPost(@RequestBody CreatePostRequest request){
        CreatePostResponse response = new CreatePostResponse();
        try{
            PostBO postBO = new PostBO();
            BeanUtils.copyProperties(request, postBO);
            int id = mainPageService.CreatePost(postBO, request.getPhotoList());
            response.setPostId(id);
            response.setResponseStatus(0);

        } catch (CommonException ex){

            logger.error(ex.getWarnMessage());

            response.setResponseStatus(ex.getReturnStatus());
            response.setErrorMessage(ex.getWarnMessage());
        }
        return response;
    }

    @RequestMapping(value = "/getpost", method = RequestMethod.POST)
    public GetPostResponse getPost(@RequestBody GetPostRequest request){
        GetPostResponse response = new GetPostResponse();
        try{
            PostBO postBO = mainPageService.getPost(request.getPostId(), request.getUserId());
            BeanUtils.copyProperties(postBO, response);
            response.setPhotoList(postBO.getPhotoList());

            if(postBO.getCreateUser() != null){
                UserPart userPart = new UserPart();
                BeanUtils.copyProperties(postBO.getCreateUser(), userPart);
                response.setCreateUser(userPart);
            }

            if(postBO.getAnswerBOList() != null && postBO.getAnswerBOList().size() > 0){
                List<AnswerPart> answerParts = new ArrayList<>();
                for(AnswerBO answerBO : postBO.getAnswerBOList()){
                    AnswerPart answerPart = new AnswerPart();
                    BeanUtils.copyProperties(answerBO, answerPart);
                    answerPart.setPhotoList(answerBO.getPhotoList());

                    if(answerBO.getUser() != null){
                        UserPart userPart = new UserPart();
                        BeanUtils.copyProperties(answerBO.getUser(), userPart);
                        answerPart.setUser(userPart);
                    }
                }

                response.setAnswerList(answerParts);
            }
        } catch (CommonException ex){
            logger.error(ex.getWarnMessage());

            response.setResponseStatus(ex.getReturnStatus());
            response.setErrorMessage(ex.getWarnMessage());
        }
        return response;
    }
}
