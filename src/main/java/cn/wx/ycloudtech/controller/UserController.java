package cn.wx.ycloudtech.controller;

import cn.wx.ycloudtech.domain.Activity;
import cn.wx.ycloudtech.domain.Organization;
import cn.wx.ycloudtech.domain.User;
import cn.wx.ycloudtech.domain.UserAct;
import cn.wx.ycloudtech.service.*;
import cn.wx.ycloudtech.util.MyConstants;
import cn.wx.ycloudtech.util.Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Component("UserController")
public class UserController {
//    private final String wxspAppid = "wx5d1a10227e49f3d1";
//    private final String wxspSecret = "be9300b9431035124df0b182cf8fc629";

    // TODO wx尚未申请
    private final String wxspAppid = "wx69ea2832a7b0e135";
    private final String wxspSecret = "ac646d21137c71771501dc21d3364ed9";
    private final String wxspAPI = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private OrganizationService organizationService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    /***************************************************************************************
     * 登录注册相关
     ***************************************************************************************/

    @PostMapping("/logout")
    public HashMap<String, Object> logout(@RequestBody String body) {
        HashMap<String, Object> ret = new HashMap<>();
        try {
            JSONObject object = JSON.parseObject(body);
            String sessionKey = object.getString("sessionKey");
            redisService.delSessionId(sessionKey);
            ret.put("state", MyConstants.RESULT_OK);
        } catch (Exception e) {
            ret.put("state", MyConstants.RESULT_FAIL);
        }
        return ret;
    }

    @PostMapping("/loginWithWechat")
    public HashMap<String, Object> loginWithWechat(@RequestBody String body) {
        HashMap<String, Object> ret = new HashMap<>();
        JSONObject object = JSONObject.parseObject(body);

        String openId = this.getOpenIdByCode(object.getString("code"));
        if (openId == null) {
            ret.put("state", MyConstants.RESULT_FAIL);
            return ret;
        }

        User user = userService.getUserById(openId);

        if (user == null) {  // 新用户注册
            user = this.createUser(object, openId);
            if (userService.insertUser(user) != 1) {
                ret.put("state", MyConstants.RESULT_FAIL);
                ret.put("info", MyConstants.RESULT_INSERT_FAIL);
                return ret;
            }
        } else {  // 老用户登录
            log.info("user " + user.getUserId() + "/" + user.getUserName() + " logined at " + new Date());
            this.updateOldUser(object, user);
            if (userService.updateUser(user) != 1) {
                ret.put("state", MyConstants.RESULT_FAIL);
                ret.put("info", MyConstants.RESULT_UPDATE_FAIL);
                return ret;
            }
        }

        ret.put("state", MyConstants.RESULT_OK);
        ret.put("openId", user.getUserId());
        ret.put("sessionKey", user.getSessionKey());
        return ret;
    }

    private String getOpenIdByCode(String code) {
        String response = "";
        try {
            RestTemplate restTemplate = new RestTemplate();
            String params = "?appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=authorization_code";
            String url = wxspAPI + params;
            response = restTemplate.getForObject(url, String.class);

            JsonNode node = this.objectMapper.readTree(response);
            return node.get("openid").asText();
        } catch (Exception ex) {
            log.error("Something Wrong When Get OpenId from code: " + code + "\n" + ex.getMessage() + "\n" +
                    " the real response is: " + response);
            return null;
        }
    }

    private User createUser(JSONObject object, String openId) {
        User user = new User();
        user.setUserId(openId);
        user.setUserRegTime(MyConstants.DATETIME_FORMAT.format(new Date()));

        this.updateOldUser(object, user);
        return user;
    }

    private void updateOldUser(JSONObject object, User user) {
        JSONObject userInfo = (JSONObject) object.get("userInfo");
        if (userInfo != null) {
            user.setUserName(userInfo.getString("nickName"));
            user.setUserGender(userInfo.getInteger("gender"));
            user.setUserAvatarUrl(userInfo.getString("avatarUrl"));
        }

        String userSessionKey = UUID.randomUUID().toString();
        user.setSessionKey(userSessionKey);

        try {
            redisService.delSessionId(user.getSessionKey());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        redisService.saveUserOrAdminBySessionId(user.getSessionKey(), user);
    }

    /**
     * 账号密码登录
     *
     * @param body 账号密码
     * @return 登陆验证结果
     */
    @PostMapping("/login")
    public HashMap<String, Object> login(@RequestBody String body) {
        HashMap<String, Object> res = new HashMap<>();
        JSONObject object = JSONObject.parseObject(body);
        String userName = object.getString("userName");
        String pwd = object.getString("pwd");

        User user = userService.getUserByPwd(userName, pwd);
        if (user == null) {
            res.put("code", MyConstants.APPLY_FAIL);
            return res;
        }
        res.put("user", user);
        res.put("code", MyConstants.APPLY_SUCCESS);

        try {
            redisService.delSessionId(user.getSessionKey());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        // 登录校验码相关
        String userSessionKey = UUID.randomUUID().toString();
        user.setSessionKey(userSessionKey);
        redisService.saveUserOrAdminBySessionId(user.getSessionKey(), user);
        res.put("sessionKey", user.getSessionKey());
        userService.updateUser(user);

        return res;
    }

    @PostMapping("/signin")
    public HashMap<String, Object> signIn(@RequestBody String body) {
        HashMap<String, Object> res = new HashMap<>();
        User user = JSON.parseObject(body, new TypeReference<User>() {
        });

        // 登陆账户名重复问题
        // todo 注意微信直接授权登录的用户，也要判断一下；目前还没用到直接授权
        int userNameNum = userService.getUserCountByName(user.getUserName());
        if (userNameNum > 0) {
            res.put("code", MyConstants.APPLY_FAIL);
            return res;
        }

        // 登录校验码、主键
        user.setSessionKey(UUID.randomUUID().toString());
        user.setUserId(UUID.randomUUID().toString());

        userService.insertUser(user);
        redisService.saveUserOrAdminBySessionId(user.getSessionKey(), user);

        res.put("user", user);
        res.put("code", MyConstants.APPLY_SUCCESS);
        res.put("sessionKey", user.getSessionKey());
        return res;
    }

    /***************************************************************************************
     * 其他
     * 核对于腾讯文档 https://docs.qq.com/sheet/DUE5wcnh0SXRBUEFt
     ***************************************************************************************/

    @PostMapping("/homepage")
    public HashMap<String, Object> homepage(@RequestBody String body) {
        HashMap<String, Object> res = new HashMap<>();
//        JSONObject object = JSONObject.parseObject(body);

        List<Activity> activityList = activityService.getAllActivity();

        for (Activity activity : activityList) {
            activity.setOrganName(organizationService.getOrgNameByUserId(activity.getUserId()));
            activity.setActPhotoList(activityService.getPhotoListByActId(activity.getActId()));
        }

        res.put("list", activityList);
        return res;
    }

    @PostMapping("/activity")
    public HashMap<String, Object> activity(@RequestBody String body) {
        HashMap<String, Object> res = new HashMap<>();
//        JSONObject object = JSONObject.parseObject(body);

        List<Activity> activityList = activityService.getAllActivity();

        for (Activity activity : activityList) {
            activity.setOrganName(organizationService.getOrgNameByUserId(activity.getUserId()));
            activity.setReviewNum(activityService.getReviewNumByActId(activity.getActId()));
            activity.setActPhotoList(activityService.getPhotoListByActId(activity.getActId()));
        }

        res.put("list", activityList);
        return res;
    }

    @PostMapping("/actDetail")
    public HashMap<String, Object> actDetail(@RequestBody String body) {
        HashMap<String, Object> res = new HashMap<>();
        JSONObject object = JSONObject.parseObject(body);
        String actId = object.getString("actId");
        Activity actRes = activityService.getActById(actId);
        actRes.setActPhotoList(activityService.getPhotoListByActId(actId));
        actRes.setOrganName(organizationService.getOrgNameByUserId(actRes.getUserId()));

        res.put("detailData", actRes);
        return res;
    }

    @PostMapping("/apply")
    public HashMap<String, Object> apply(@RequestBody String body) {
        HashMap<String, Object> res = new HashMap<>();
        JSONObject object = JSONObject.parseObject(body);
        String userId = object.getString("userId");
        String actId = object.getString("actId");

        Integer addRes = activityService.addUserAct(userId, actId);

        res.put("code", addRes);
//        res.put("message", addRes.equals(MyConstants.APPLY_SUBMIT) ? "报名成功" : "报名失败");

        return res;
    }

    @PostMapping("/activitycom")
    public HashMap<String, Object> activityCom(@RequestBody String body) {
        HashMap<String, Object> res = new HashMap<>();
        JSONObject object = JSONObject.parseObject(body);
        String actId = object.getString("actId");

        Activity activity = activityService.getActById(actId);

        res.put("actName", activity.getActName());
        res.put("organName", organizationService.getOrgNameByUserId(activity.getUserId()));
        res.put("actPhotoList", activityService.getPhotoListByActId(actId));
        res.put("reviewList", activityService.getReviewsByActId(actId));

        return res;
    }

    @PostMapping("/comdetail")
    public HashMap<String, Object> comDetail(@RequestBody String body) {
        HashMap<String, Object> res = new HashMap<>();
        JSONObject object = JSONObject.parseObject(body);
        String actId = object.getString("actId");
        String userId = object.getString("userId");

        Activity activity = activityService.getActById(actId);

        res.put("actName", activity.getActName());
        res.put("actDemands", activity.getActDemands());
        res.put("organName", organizationService.getOrgNameByUserId(activity.getUserId()));
        res.put("actPhotoList", activityService.getPhotoListByActId(actId));

        res.put("userName", userService.getUserById(userId).getUserName());

        UserAct userAct = activityService.getUserActByUserAndActId(userId, actId);
        if (userAct != null && userAct.getReviewTime() != null && !userAct.getReviewTime().equals("")) {
            res.put("reviewContent", userAct.getReviewContent());
            res.put("reviewTime", userAct.getReviewTime());
            res.put("reviewPhotoList", userAct.getReviewPhotoList());
        }

        return res;
    }

    @PostMapping("/applyfor")
    public HashMap<String, Object> applyfor(@RequestBody String body) {
        HashMap<String, Object> res = new HashMap<>();
        Organization organizationNew = JSON.parseObject(body, new TypeReference<Organization>() {
        });

        String userId = organizationNew.getUserId();
        if (organizationService.ifUserHasOrgan(userId)) {
            // TODO 前端说不做管理端了，就没招募者的资质审核了；这里先这样写了，留作拓展。
            res.put("code", "1");
//            res.put("message", "认证失败");
        } else {
            organizationNew.setOrganId(UUID.randomUUID().toString());
            organizationNew.setOrganStatus(MyConstants.ORGAN_PASS);
            organizationService.addNewOrgan(organizationNew);
            // TODO 前端说不做管理端了，就没招募者的资质审核了；这里先这样写了，留作拓展。
            res.put("code", "0");
//            res.put("message", "认证成功");
        }
        return res;
    }

    @PostMapping("/createact")
    public HashMap<String, Object> createAct(@RequestBody String body) {  // 用户发起的活动
        HashMap<String, Object> res = new HashMap<>();
        JSONObject object = JSONObject.parseObject(body);
        String userId = object.getString("userId");
//        String actId = object.getString("actId");
        List<Activity> activityList = activityService.getUserOwnedAct(userId);
        res.put("list", activityList);
        return res;
    }

    @PostMapping("/createdetail")
    public HashMap<String, Object> createDetail(@RequestBody String body) {  // 用户发起活动...
        HashMap<String, Object> res = new HashMap<>();
        Activity actNew = JSON.parseObject(body, new TypeReference<Activity>() {
        });
        actNew.setActId(UUID.randomUUID().toString());
        activityService.addActNew(actNew);

        res.put("code", "0");
//        res.put("message", "提交成功");
        return res;
    }

    @PostMapping("/myactivity")
    public HashMap<String, Object> myActivity(@RequestBody String body) {  // 用户参与的活动
        HashMap<String, Object> res = new HashMap<>();
        JSONObject object = JSONObject.parseObject(body);
        String userId = object.getString("userId");
//        String actId = object.getString("actId");
        List<Activity> activityList = activityService.getUserJoinedAct(userId);
        res.put("list", activityList);
        return res;
    }

    @PostMapping("/check")
    public HashMap<String, Object> check(@RequestBody String body) {  // 用户发起活动...
        HashMap<String, Object> res = new HashMap<>();
        JSONObject object = JSONObject.parseObject(body);
        String userId = object.getString("userId");
        String actId = object.getString("actId");
        UserAct userAct = activityService.getUserActByUserAndActId(userId, actId);
        if (userAct != null) {
            if (userAct.getApplyStatus().equals(MyConstants.USER_ACT_CHECK_NEED)) {  // 待签到
                userAct.setCheckTime(MyConstants.DATETIME_FORMAT.format(new Date()));
                userAct.setApplyStatus(MyConstants.USER_ACT_CHECKED);
                res.put("code", "0");
            } else/* if(userAct.getApplyStatus().equals(MyConstants.USER_ACT_CHECKED))*/ { // 时间点无需签到
                res.put("code", "1");
            }
        } else { // 无需签到
            res.put("code", "1");
        }
        return res;
    }

    @PostMapping("/createreview")
    public HashMap<String, Object> createReview(@RequestBody String body) {  // 用户发起活动...
        HashMap<String, Object> res = new HashMap<>();
        JSONObject object = JSONObject.parseObject(body);
        String actId = object.getString("actId");
        String userId = object.getString("userId");
        UserAct userAct = activityService.getUserActByUserAndActId(userId, actId);
        if (userAct != null) {
            if ((userAct.getApplyStatus().equals(MyConstants.USER_ACT_CHECKED) ||
                    userAct.getApplyStatus().equals(MyConstants.USER_ACT_END)) &&
                    Util.isEmpty(userAct.getReviewTime())) {  // 可以评论
                userAct.setReviewTime(MyConstants.DATETIME_FORMAT.format(new Date()));
                userAct.setReviewContent(object.getString("reviewContent"));
                res.put("code", "0");
            } else/* if(userAct.getApplyStatus().equals(MyConstants.USER_ACT_CHECKED))*/ { // 时间点不能评论
                res.put("code", "1");
            }
        } else { // 无需签到
            res.put("code", "1");
        }
        return res;
    }


    /***************************************************************************************
     * 其他
     * 2021.05.14后新增四个接口
     ***************************************************************************************/
    
    @PostMapping("/admin/applylist")
    public HashMap<String, Object> AdminApplyList(@RequestBody String body) {  // 若是管理员的ID，就返回待认证列表。。。
        HashMap<String, Object> res = new HashMap<>();
        JSONObject object = JSONObject.parseObject(body);
        String userId = object.getString("userId");
        User user = userService.getUserById(userId);
        if (user.getUserIfSuper().equals(MyConstants.USER_SUPER_YES)) {
            res.put("code", 1);
            List<Organization> organizationList =
                    organizationService.getAllSubmitOrgan();
            res.put("list", organizationList);
        } else {
            res.put("code", 0);
        }
        return res;
    }

    @PostMapping("/admin/applydetail")
    public HashMap<String, Object> AdminApplyDetail(@RequestBody String body) {  // 直接返回用户userId对应的申请组织列表
        HashMap<String, Object> res = new HashMap<>();
        JSONObject object = JSONObject.parseObject(body);
        String userId = object.getString("userId");
        List<Organization> organizationList = organizationService.getOrganByUserId(userId);
        res.put("list", organizationList);
        return res;
    }

    @PostMapping("/admin/required")
    public HashMap<String, Object> AdminRequired(@RequestBody String body) {  // 用户0通过认证；1否定认证。没看出来跟下面的区别
        HashMap<String, Object> res = new HashMap<>();
        JSONObject object = JSONObject.parseObject(body);
        String userId = object.getString("userId");
        List<Organization> organizationList = organizationService.getOrganByUserId(userId);
        if (organizationList.size() > 0 &&
                organizationList.get(0).getOrganStatus().equals(MyConstants.ORGAN_PASS)) {
            res.put("code", 1);
        } else {
            res.put("code", 0);
        }
        return res;
    }

    @PostMapping("/organized")
    public HashMap<String, Object> organized(@RequestBody String body) {  // 判断用户是否是招募者0是；1否。没看出来跟上面得区别
        HashMap<String, Object> res = new HashMap<>();
        JSONObject object = JSONObject.parseObject(body);
        String userId = object.getString("userId");
        List<Organization> organizationList = organizationService.getOrganByUserId(userId);
        if (organizationList.size() > 0 &&
                organizationList.get(0).getOrganStatus().equals(MyConstants.ORGAN_PASS)) {
            res.put("code", 1);
        } else {
            res.put("code", 0);
        }
        return res;
    }

}
