package cn.wx.ycloudtech.domain;

import com.baomidou.mybatisplus.annotations.TableId;

import java.io.Serializable;

public class User implements Serializable {
    /** 主键ID;本表主键ID */
    @TableId
    private String userId ;
    /** 登录账户 */
    private String userName ;
    /** 登录密码 */
    private String userPwd ;
    /** 用户昵称 */
    private String userNickname ;
    /** 微信openId */
    private String userOpenId ;
    /** 用户性别 */
    private Integer userGender ;
    /** 用户年龄 */
    private Integer userAge ;
    /** 用户职业 */
    private String userCareer ;
    /** 用户手机号码 */
    private String userTel ;
    /** 用户地址 */
    private String userAddr ;
    /** 微信头像 */
    private String userAvatarUrl ;
    /** 是否被金庸 */
    private String userIfBanned ;
    /** 招募资质 */
    private String userIfOrgan ;
    /** 运维身份 */
    private String userIfSuper ;
    /** 注册时间 */
    private String userRegTime ;
    /** 登陆经度 */
    private Double userLongitude ;
    /** 登陆纬度 */
    private Double userLatitude ;
    /** 登录状态码 */
    private String sessionKey ;

    /** 主键ID;本表主键ID */
    public String getUserId(){
        return this.userId;
    }
    /** 主键ID;本表主键ID */
    public void setUserId(String userId){
        this.userId = userId;
    }
    /** 登录账户 */
    public String getUserName(){
        return this.userName;
    }
    /** 登录账户 */
    public void setUserName(String userName){
        this.userName = userName;
    }
    /** 登录密码 */
    public String getUserPwd(){
        return this.userPwd;
    }
    /** 登录密码 */
    public void setUserPwd(String userPwd){
        this.userPwd = userPwd;
    }
    /** 用户昵称 */
    public String getUserNickname(){
        return this.userNickname;
    }
    /** 用户昵称 */
    public void setUserNickname(String userNickname){
        this.userNickname = userNickname;
    }
    /** 微信openId */
    public String getUserOpenId(){
        return this.userOpenId;
    }
    /** 微信openId */
    public void setUserOpenId(String userOpenId){
        this.userOpenId = userOpenId;
    }
    /** 用户性别 */
    public Integer getUserGender(){
        return this.userGender;
    }
    /** 用户性别 */
    public void setUserGender(Integer userGender){
        this.userGender = userGender;
    }
    /** 用户年龄 */
    public Integer getUserAge(){
        return this.userAge;
    }
    /** 用户年龄 */
    public void setUserAge(Integer userAge){
        this.userAge = userAge;
    }
    /** 用户职业 */
    public String getUserCareer(){
        return this.userCareer;
    }
    /** 用户职业 */
    public void setUserCareer(String userCareer){
        this.userCareer = userCareer;
    }
    /** 用户手机号码 */
    public String getUserTel(){
        return this.userTel;
    }
    /** 用户手机号码 */
    public void setUserTel(String userTel){
        this.userTel = userTel;
    }
    /** 用户地址 */
    public String getUserAddr(){
        return this.userAddr;
    }
    /** 用户地址 */
    public void setUserAddr(String userAddr){
        this.userAddr = userAddr;
    }
    /** 微信头像 */
    public String getUserAvatarUrl(){
        return this.userAvatarUrl;
    }
    /** 微信头像 */
    public void setUserAvatarUrl(String userAvatarUrl){
        this.userAvatarUrl = userAvatarUrl;
    }
    /** 是否被金庸 */
    public String getUserIfBanned(){
        return this.userIfBanned;
    }
    /** 是否被金庸 */
    public void setUserIfBanned(String userIfBanned){
        this.userIfBanned = userIfBanned;
    }
    /** 招募资质 */
    public String getUserIfOrgan(){
        return this.userIfOrgan;
    }
    /** 招募资质 */
    public void setUserIfOrgan(String userIfOrgan){
        this.userIfOrgan = userIfOrgan;
    }
    /** 运维身份 */
    public String getUserIfSuper(){
        return this.userIfSuper;
    }
    /** 运维身份 */
    public void setUserIfSuper(String userIfSuper){
        this.userIfSuper = userIfSuper;
    }
    /** 注册时间 */
    public String getUserRegTime(){
        return this.userRegTime;
    }
    /** 注册时间 */
    public void setUserRegTime(String userRegTime){
        this.userRegTime = userRegTime;
    }
    /** 登陆经度 */
    public Double getUserLongitude(){
        return this.userLongitude;
    }
    /** 登陆经度 */
    public void setUserLongitude(Double userLongitude){
        this.userLongitude = userLongitude;
    }
    /** 登陆纬度 */
    public Double getUserLatitude(){
        return this.userLatitude;
    }
    /** 登陆纬度 */
    public void setUserLatitude(Double userLatitude){
        this.userLatitude = userLatitude;
    }
    /** 登录状态码 */
    public String getSessionKey(){
        return this.sessionKey;
    }
    /** 登录状态码 */
    public void setSessionKey(String sessionKey){
        this.sessionKey = sessionKey;
    }
}
