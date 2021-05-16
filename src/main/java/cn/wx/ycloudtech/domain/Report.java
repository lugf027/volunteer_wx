package cn.wx.ycloudtech.domain;

import cn.wx.ycloudtech.util.MyConstants;
import com.baomidou.mybatisplus.annotations.TableId;

public class Report {
    /** 主键ID;本表主键ID */
    @TableId
    private String reportId ;
    /** 用户ID */
    private String userId ;
    /** 被投诉的ID */
    private String reportedId ;
    /** 被投诉者类型;0用户；1活动 */
    private String reportType;
    /** 投诉文本内容 */
    private String reportReason ;
    /** 投诉时间 */
    private String reportTime ;
    /** 投诉处理状态;0待处理；1投诉成功；2投诉失败 */
    private String reportStatus = MyConstants.REPORT_SUBMIT;
    /** 投诉处理人 */
    private String reportHandler ;

    /** 主键ID;本表主键ID */
    public String getReportId(){
        return this.reportId;
    }
    /** 主键ID;本表主键ID */
    public void setReportId(String reportId){
        this.reportId = reportId;
    }
    /** 用户ID */
    public String getUserId(){
        return this.userId;
    }
    /** 用户ID */
    public void setUserId(String userId){
        this.userId = userId;
    }
    /** 被投诉的ID */
    public String getReportedId(){
        return this.reportedId;
    }
    /** 被投诉的ID */
    public void setReportedId(String reportedId){
        this.reportedId = reportedId;
    }
    /** 被投诉者类型;0用户；1活动 */
    public String getReportType(){
        return this.reportType;
    }
    /** 被投诉者类型;0用户；1活动 */
    public void setReportType(String reportType){
        this.reportType = reportType;
    }
    /** 投诉文本内容 */
    public String getReportReason(){
        return this.reportReason;
    }
    /** 投诉文本内容 */
    public void setReportReason(String reportReason){
        this.reportReason = reportReason;
    }
    /** 投诉时间 */
    public String getReportTime(){
        return this.reportTime;
    }
    /** 投诉时间 */
    public void setReportTime(String reportTime){
        this.reportTime = reportTime;
    }
    /** 投诉处理状态;0待处理；1投诉成功；2投诉失败 */
    public String getReportStatus(){
        return this.reportStatus;
    }
    /** 投诉处理状态;0待处理；1投诉成功；2投诉失败 */
    public void setReportStatus(String reportStatus){
        this.reportStatus = reportStatus;
    }
    /** 投诉处理人 */
    public String getReportHandler(){
        return this.reportHandler;
    }
    /** 投诉处理人 */
    public void setReportHandler(String reportHandler){
        this.reportHandler = reportHandler;
    }
}
