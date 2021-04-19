package cn.wx.ycloudtech.domain;

import com.baomidou.mybatisplus.annotations.TableId;

public class UserAct {
    /** 主键ID;本表主键ID */
    @TableId
    private String userActId ;
    /** 用户ID */
    private String userId ;
    /** 活动ID */
    private String actId ;
    /** 申请时间 */
    private String applyTime ;
    /** 申请状态;0：已提交申请；1：活动已取消；2：活动待开始；3：待签到；4：进行中；5：已完成 */
    private String applyStatus ;
    /** 签到时间 */
    private String checkTime ;
    /** 签到经度 */
    private Double checkLongitude ;
    /** 签到纬度 */
    private Double checkLatitude ;
    /** 评价内容 */
    private String reviewContent ;
    /** 评价时间 */
    private String reviewTime ;

    /** 主键ID;本表主键ID */
    public String getUserActId(){
        return this.userActId;
    }
    /** 主键ID;本表主键ID */
    public void setUserActId(String userActId){
        this.userActId = userActId;
    }
    /** 用户ID */
    public String getUserId(){
        return this.userId;
    }
    /** 用户ID */
    public void setUserId(String userId){
        this.userId = userId;
    }
    /** 活动ID */
    public String getActId(){
        return this.actId;
    }
    /** 活动ID */
    public void setActId(String actId){
        this.actId = actId;
    }
    /** 申请时间 */
    public String getApplyTime(){
        return this.applyTime;
    }
    /** 申请时间 */
    public void setApplyTime(String applyTime){
        this.applyTime = applyTime;
    }
    /** 申请状态;0：已提交申请；1：活动已取消；2：活动待开始；3：待签到；4：进行中；5：已完成 */
    public String getApplyStatus(){
        return this.applyStatus;
    }
    /** 申请状态;0：已提交申请；1：活动已取消；2：活动待开始；3：待签到；4：进行中；5：已完成 */
    public void setApplyStatus(String applyStatus){
        this.applyStatus = applyStatus;
    }
    /** 签到时间 */
    public String getCheckTime(){
        return this.checkTime;
    }
    /** 签到时间 */
    public void setCheckTime(String checkTime){
        this.checkTime = checkTime;
    }
    /** 签到经度 */
    public Double getCheckLongitude(){
        return this.checkLongitude;
    }
    /** 签到经度 */
    public void setCheckLongitude(Double checkLongitude){
        this.checkLongitude = checkLongitude;
    }
    /** 签到纬度 */
    public Double getCheckLatitude(){
        return this.checkLatitude;
    }
    /** 签到纬度 */
    public void setCheckLatitude(Double checkLatitude){
        this.checkLatitude = checkLatitude;
    }
    /** 评价内容 */
    public String getReviewContent(){
        return this.reviewContent;
    }
    /** 评价内容 */
    public void setReviewContent(String reviewContent){
        this.reviewContent = reviewContent;
    }
    /** 评价时间 */
    public String getReviewTime(){
        return this.reviewTime;
    }
    /** 评价时间 */
    public void setReviewTime(String reviewTime){
        this.reviewTime = reviewTime;
    }
}
