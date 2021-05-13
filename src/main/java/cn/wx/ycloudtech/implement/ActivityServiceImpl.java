package cn.wx.ycloudtech.implement;

import cn.wx.ycloudtech.domain.Activity;
import cn.wx.ycloudtech.domain.File;
import cn.wx.ycloudtech.domain.UserAct;
import cn.wx.ycloudtech.mapper.ActivityMapper;
import cn.wx.ycloudtech.mapper.FileMapper;
import cn.wx.ycloudtech.mapper.UserActMapper;
import cn.wx.ycloudtech.mapper.UserMapper;
import cn.wx.ycloudtech.service.ActivityService;
import cn.wx.ycloudtech.util.MyConstants;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("ActivityService")
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private UserActMapper userActMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Activity> getAllActivity() {
        return activityMapper.selectList(new EntityWrapper<Activity>()
                .orderBy("ACT_REC_BEGIN", false));
    }

    @Override
    public Integer getReviewNumByActId(String actId) {
        return userActMapper.selectCount(new EntityWrapper<UserAct>()
                .eq("ACT_ID", actId)
                .isNotNull("REVIEW_TIME"));
    }

    @Override
    public List<String> getPhotoListByActId(String actId) {
        return getPhotoListById(actId, MyConstants.FILE_LINK_ACT);
    }

    @Override
    public Integer addUserAct(String userId, String actId) {
        UserAct userAct = new UserAct();
        userAct.setActId(actId);
        userAct.setUserId(userId);
        userAct.setUserActId(UUID.randomUUID().toString());
        userAct.setApplyTime(MyConstants.DATETIME_FORMAT.format(new Date()));

        Activity activity = activityMapper.selectById(actId);
        Integer userActNum = userActMapper.selectCount(new EntityWrapper<UserAct>()
                .eq("ACT_ID", actId));
        // TODO 这里会有多线程问题，亦即获得的 userActNum 可能过时了
        if (Integer.parseInt(activity.getActUserNum()) > userActNum) {
            userActMapper.insert(userAct);

            if (1 == Integer.parseInt(activity.getActUserNum()) - userActNum) {
                List<UserAct> userActList = getUserActByActId(activity.getActId());
                for (UserAct userActToUpdate : userActList) {
                    if (userActToUpdate.getApplyStatus().equals(MyConstants.USER_ACT_APPLY)) {
                        userActToUpdate.setApplyStatus(MyConstants.USER_ACT_APPLY_SUCCESS);
                        updateUserAct(userActToUpdate);
                    }
                }
            }

            return MyConstants.APPLY_SUCCESS;
        } else {
            return MyConstants.APPLY_FAIL;
        }
    }

    @Override
    public Activity getActById(String actId) {
        return activityMapper.selectById(actId);
    }

    @Override
    public List<UserAct> getReviewsByActId(String actId) {
        List<UserAct> userActListRes = userActMapper.selectList(new EntityWrapper<UserAct>()
                .eq("ACT_ID", actId)
                .isNotNull("REVIEW_TIME"));

        for (UserAct userAct : userActListRes) {
            userAct.setReviewPhotoList(getPhotoListById(userAct.getUserActId(), MyConstants.FILE_LINK_REVIEW));
            userAct.setUserName(userMapper.selectById(userAct.getUserId()).getUserName());
        }
        return userActListRes;
    }

    @Override
    public UserAct getUserActByUserAndActId(String userId, String actId) {
        List<UserAct> userActList = userActMapper.selectList(new EntityWrapper<UserAct>()
                .eq("USER_ID", userId)
                .eq("ACT_ID", actId));
        if (userActList.isEmpty())
            return null;
        else {
            userActList.get(0).setReviewPhotoList(
                    getPhotoListById(userActList.get(0).getUserActId(), MyConstants.FILE_LINK_REVIEW)
            );
            return userActList.get(0);
        }
    }

    @Override
    public List<Activity> getUserOwnedAct(String userId) {
        return activityMapper.selectList(new EntityWrapper<Activity>()
                .eq("USER_ID", userId));
    }

    @Override
    public List<Activity> getUserJoinedAct(String userId) {
        List<UserAct> userActList = userActMapper.selectList(new EntityWrapper<UserAct>()
                .eq("USER_ID", userId));
        List<Activity> actListRes = new ArrayList<>();
        for (UserAct userAct : userActList) {
            // TODO 这里其实有活动删除的问题
            actListRes.add(getActById(userAct.getActId()));
        }
        return actListRes;
    }

    @Override
    public Integer addActNew(Activity actNew) {
        return activityMapper.insert(actNew);
    }

    @Override
    public Integer addFileNew(File fileDomain) {
        return fileMapper.insert(fileDomain);
    }

    @Override
    public List<Activity> getAllActNeedUpdateToday(String dateToday, String dateType) {
        return activityMapper.selectList(new EntityWrapper<Activity>()
                .eq(dateType, dateToday));
    }

    @Override
    public Integer updateOneAct(Activity activity) {
        return activityMapper.updateById(activity);
    }

    @Override
    public Integer getUserActNumByActId(String actId) {
        return userActMapper.selectCount(new EntityWrapper<UserAct>()
                .eq("ACT_ID", actId));
    }

    @Override
    public List<UserAct> getUserActByActId(String actId) {
        return userActMapper.selectList(new EntityWrapper<UserAct>()
                .eq("ACT_ID", actId));
    }

    @Override
    public Integer updateUserAct(UserAct userAct) {
        return userActMapper.updateById(userAct);
    }

    /**
     * 根绝linkId与linkType 获得url list
     */
    private List<String> getPhotoListById(String linkId, String linkType) {
        List<String> pPhotoListRes = new ArrayList<>();
        List<File> fileList = fileMapper.selectList(new EntityWrapper<File>()
                .eq("LINK_ID", linkId)
                .eq("LINK_TYPE", linkType));
        for (File file : fileList)
            pPhotoListRes.add(file.getFileAddr());
        return pPhotoListRes;
    }

}
