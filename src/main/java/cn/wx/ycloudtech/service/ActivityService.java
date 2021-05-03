package cn.wx.ycloudtech.service;

import cn.wx.ycloudtech.domain.Activity;
import cn.wx.ycloudtech.domain.File;
import cn.wx.ycloudtech.domain.UserAct;

import java.util.List;

public interface ActivityService {
    List<Activity> getAllActivity();

    Integer getReviewNumByActId(String actId);

    List<String> getPhotoListByActId(String actId);

    Integer addUserAct(String userId, String actId);

    Activity getActById(String actId);

    List<UserAct> getReviewsByActId(String actId);

    UserAct getUserActByUserAndActId(String userId, String actId);

    List<Activity> getUserOwnedAct(String userId);

    List<Activity> getUserJoinedAct(String userId);

    Integer addActNew(Activity actNew);

    Integer addFileNew(File fileDomain);
}
