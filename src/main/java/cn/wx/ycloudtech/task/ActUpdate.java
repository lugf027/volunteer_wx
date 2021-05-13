package cn.wx.ycloudtech.task;

import cn.wx.ycloudtech.domain.Activity;
import cn.wx.ycloudtech.domain.UserAct;
import cn.wx.ycloudtech.service.ActivityService;
import cn.wx.ycloudtech.util.MyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

public class ActUpdate {
    @Autowired
    private ActivityService activityService;

    private static final String actRecBegin = "ACT_REC_BEGIN";
    private static final String actRecEnd = "ACT_REC_END";
    private static final String actTimeBegin = "ACT_TIME_BEGIN";
    private static final String actTimeEnd = "ACT_TIME_END";


    @Scheduled(cron = "${jobs.act.update}")
    public void updateActivity() {
        String dateToday = MyConstants.DATE_FORMAT.format(new Date());
        String dateYesterday = MyConstants.DATE_FORMAT.format(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24));
        List<Activity> activityListRecStartToday = activityService.getAllActNeedUpdateToday(dateToday, actRecBegin);
        List<Activity> activityListRecEndToday = activityService.getAllActNeedUpdateToday(dateYesterday, actRecEnd);

        List<Activity> activityListActStartToday = activityService.getAllActNeedUpdateToday(dateToday, actTimeBegin);
        List<Activity> activityListActEndToday = activityService.getAllActNeedUpdateToday(dateYesterday, actTimeEnd);

        handleActListRecStartToday(activityListRecStartToday);
        handleActListRecEndToday(activityListRecEndToday);
        handleActListActStartToday(activityListActStartToday);
        handleActListActEndToday(activityListActEndToday);
    }

    private void handleActListRecStartToday(List<Activity> activityList) {
        for (Activity activity : activityList) {
            if (activity.getActStatus().equals(MyConstants.ACT_SUBMIT)) {
                activity.setActStatus(MyConstants.ACT_RECRUITING);
                activityService.updateOneAct(activity);
                // 更新userAct status apply_suc 为 needAct
                List<UserAct> userActList = activityService.getUserActByActId(activity.getActId());
                for (UserAct userAct : userActList) {
                    if (userAct.getApplyStatus().equals(MyConstants.USER_ACT_APPLY_SUCCESS)) {
                        userAct.setApplyStatus(MyConstants.USER_ACT_CHECK_NEED);
                        activityService.updateUserAct(userAct);
                    }
                }
            }
        }
    }

    private void handleActListRecEndToday(List<Activity> activityList) {
        for (Activity activity : activityList) {
            if (activity.getActStatus().equals(MyConstants.ACT_RECRUITING)) {
                final Integer userNumNow = activityService.getUserActNumByActId(activity.getActId());
                if (userNumNow < Integer.parseInt(activity.getActUserNum())) {  // 招募人数不足
                    activity.setActStatus(MyConstants.ACT_RECRUIT_FAIL);
                } else if (userNumNow >= Integer.parseInt(activity.getActUserNum())) {  // 招募人数充足
                    activity.setActStatus(MyConstants.ACT_RECRUIT_SUCCESS);
                }
                activityService.updateOneAct(activity);
            }
        }
    }

    private void handleActListActStartToday(List<Activity> activityList) {
        for (Activity activity : activityList) {
            if (activity.getActStatus().equals(MyConstants.ACT_RECRUIT_SUCCESS)) {
                activity.setActStatus(MyConstants.ACT_DURING);
                activityService.updateOneAct(activity);
            }
        }
    }

    private void handleActListActEndToday(List<Activity> activityList) {
        for (Activity activity : activityList) {
            if (activity.getActStatus().equals(MyConstants.ACT_DURING)) {
                activity.setActStatus(MyConstants.ACT_END);
                activityService.updateOneAct(activity);
                // 更新userAct status checked 为 end
                List<UserAct> userActList = activityService.getUserActByActId(activity.getActId());
                for (UserAct userAct : userActList) {
                    if (userAct.getApplyStatus().equals(MyConstants.USER_ACT_CHECKED)) {
                        userAct.setApplyStatus(MyConstants.USER_ACT_END);
                        activityService.updateUserAct(userAct);
                    }
                }
            }
        }
    }
}
