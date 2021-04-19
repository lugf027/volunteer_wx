package cn.wx.ycloudtech.domain;

import com.baomidou.mybatisplus.annotations.TableId;

public class Organization {
    /** 主键ID;本表主键ID */
    @TableId
    private String organId ;
    /** 所属用户ID */
    private String userId ;
    /** 组织名称 */
    private String organName ;
    /** 组织地址 */
    private String organAddr ;
    /** 组织电话 */
    private String organTel ;
    /** 组织简介 */
    private String organIntro ;
    /** 组织申请状态;0：提交申请待审核；1：通过；2：失败 */
    private String organStatus ;

    /** 主键ID;本表主键ID */
    public String getOrganId(){
        return this.organId;
    }
    /** 主键ID;本表主键ID */
    public void setOrganId(String organId){
        this.organId = organId;
    }
    /** 所属用户ID */
    public String getUserId(){
        return this.userId;
    }
    /** 所属用户ID */
    public void setUserId(String userId){
        this.userId = userId;
    }
    /** 组织名称 */
    public String getOrganName(){
        return this.organName;
    }
    /** 组织名称 */
    public void setOrganName(String organName){
        this.organName = organName;
    }
    /** 组织地址 */
    public String getOrganAddr(){
        return this.organAddr;
    }
    /** 组织地址 */
    public void setOrganAddr(String organAddr){
        this.organAddr = organAddr;
    }
    /** 组织电话 */
    public String getOrganTel(){
        return this.organTel;
    }
    /** 组织电话 */
    public void setOrganTel(String organTel){
        this.organTel = organTel;
    }
    /** 组织简介 */
    public String getOrganIntro(){
        return this.organIntro;
    }
    /** 组织简介 */
    public void setOrganIntro(String organIntro){
        this.organIntro = organIntro;
    }
    /** 组织申请状态;0：提交申请待审核；1：通过；2：失败 */
    public String getOrganStatus(){
        return this.organStatus;
    }
    /** 组织申请状态;0：提交申请待审核；1：通过；2：失败 */
    public void setOrganStatus(String organStatus){
        this.organStatus = organStatus;
    }
}
