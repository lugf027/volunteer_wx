package cn.wx.ycloudtech.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;

import java.util.List;

public class Activity {
    /** 主键ID;本表主键ID */
    @TableId
    private String actId ;
    /** 发起者ID */
    private String userId ;
    /** 活动名称 */
    private String actName ;
    /** 活动地址 */
    private String actAddr ;
    /** 申请开始时间 */
    private String actRecBegin ;
    /** 申请截止时间 */
    private String actRecEnd ;
    /** 活动开始时间 */
    private String actTimeBegin ;
    /** 活动截止时间 */
    private String actTimeEnd ;
    /** 需求人数 */
    private String actUserNum ;
    /** 活动内容与要求 */
    private String actDemands ;
    /** 活动状态;0：已提交；1：招募中；2：招募成功待开始；3：招募失败待处理；4：已取消；5：进行中；6：已结束 */
    private String actStatus ;
    /** 签到经度 */
    private Double checkLongitude ;
    /** 签到纬度 */
    private Double checkLatitude ;

    @TableField(exist = false)
    private String organName;

    @TableField(exist = false)
    private Integer reviewNum;

    @TableField(exist = false)
    private List<String> actPhotoList;

    public List<String> getActPhotoList() {
        return actPhotoList;
    }

    public void setActPhotoList(List<String> actPhotoList) {
        this.actPhotoList = actPhotoList;
    }

    public Integer getReviewNum() {
        return reviewNum;
    }

    public void setReviewNum(Integer reviewNum) {
        this.reviewNum = reviewNum;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    /** 主键ID;本表主键ID */
    public String getActId(){
        return this.actId;
    }
    /** 主键ID;本表主键ID */
    public void setActId(String actId){
        this.actId = actId;
    }
    /** 发起者ID */
    public String getUserId(){
        return this.userId;
    }
    /** 发起者ID */
    public void setUserId(String userId){
        this.userId = userId;
    }
    /** 活动名称 */
    public String getActName(){
        return this.actName;
    }
    /** 活动名称 */
    public void setActName(String actName){
        this.actName = actName;
    }
    /** 活动地址 */
    public String getActAddr(){
        return this.actAddr;
    }
    /** 活动地址 */
    public void setActAddr(String actAddr){
        this.actAddr = actAddr;
    }
    /** 申请开始时间 */
    public String getActRecBegin(){
        return this.actRecBegin;
    }
    /** 申请开始时间 */
    public void setActRecBegin(String actRecBegin){
        this.actRecBegin = actRecBegin;
    }
    /** 申请截止时间 */
    public String getActRecEnd(){
        return this.actRecEnd;
    }
    /** 申请截止时间 */
    public void setActRecEnd(String actRecEnd){
        this.actRecEnd = actRecEnd;
    }
    /** 活动开始时间 */
    public String getActTimeBegin(){
        return this.actTimeBegin;
    }
    /** 活动开始时间 */
    public void setActTimeBegin(String actTimeBegin){
        this.actTimeBegin = actTimeBegin;
    }
    /** 活动截止时间 */
    public String getActTimeEnd(){
        return this.actTimeEnd;
    }
    /** 活动截止时间 */
    public void setActTimeEnd(String actTimeEnd){
        this.actTimeEnd = actTimeEnd;
    }
    /** 需求人数 */
    public String getActUserNum(){
        return this.actUserNum;
    }
    /** 需求人数 */
    public void setActUserNum(String actUserNum){
        this.actUserNum = actUserNum;
    }
    /** 活动内容与要求 */
    public String getActDemands(){
        return this.actDemands;
    }
    /** 活动内容与要求 */
    public void setActDemands(String actDemands){
        this.actDemands = actDemands;
    }
    /** 活动状态;0：已提交；1：招募中；2：招募成功待开始；3：招募失败待处理；4：已取消；5：进行中；6：已结束 */
    public String getActStatus(){
        return this.actStatus;
    }
    /** 活动状态;0：已提交；1：招募中；2：招募成功待开始；3：招募失败待处理；4：已取消；5：进行中；6：已结束 */
    public void setActStatus(String actStatus){
        this.actStatus = actStatus;
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
}
