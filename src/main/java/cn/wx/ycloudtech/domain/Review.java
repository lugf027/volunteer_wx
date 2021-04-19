package cn.wx.ycloudtech.domain;

import com.baomidou.mybatisplus.annotations.TableId;

public class Review {
    /** 主键ID;本表主键ID */
    @TableId
    private String reviewId ;
    /** 用户ID */
    private String userId ;
    /** 活动ID */
    private String actId ;
    /** 评论内容 */
    private String reviewContent ;
    /** 评论时间 */
    private String reviewTime ;

    /** 主键ID;本表主键ID */
    public String getReviewId(){
        return this.reviewId;
    }
    /** 主键ID;本表主键ID */
    public void setReviewId(String reviewId){
        this.reviewId = reviewId;
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
    /** 评论内容 */
    public String getReviewContent(){
        return this.reviewContent;
    }
    /** 评论内容 */
    public void setReviewContent(String reviewContent){
        this.reviewContent = reviewContent;
    }
    /** 评论时间 */
    public String getReviewTime(){
        return this.reviewTime;
    }
    /** 评论时间 */
    public void setReviewTime(String reviewTime){
        this.reviewTime = reviewTime;
    }
}
