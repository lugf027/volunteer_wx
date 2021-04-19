package cn.wx.ycloudtech.controller;

import cn.wx.ycloudtech.domain.Parameter;
import cn.wx.ycloudtech.domain.User;
import cn.wx.ycloudtech.service.ParameterService;
import cn.wx.ycloudtech.service.RedisService;
import cn.wx.ycloudtech.service.UserService;
import cn.wx.ycloudtech.util.MyConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Component("UserController")
public class UserController {

    // TODO wx 尚未申请
    private final String wxspAppid = "";
    private final String wxspSecret = "";
    private final String wxspAPI = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ParameterService parameterService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    // 测试能否连接到数据库 --> 2021.04.18 测试通过
    @PostMapping("/getParamValueByName")
    public HashMap<String, Object> getParamValueByName(@RequestBody String body) {
        HashMap<String, Object> res = new HashMap<>();
        JSONObject object = JSONObject.parseObject(body);
        String paramName = object.getString("paramName");
        Parameter parameter = parameterService.getParamValueByName(paramName);
        res.put("paramValue", parameter.getParamValue());
        return res;
    }

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

    @PostMapping("/login")
    public HashMap<String, Object> login(@RequestBody String body) {
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
}
