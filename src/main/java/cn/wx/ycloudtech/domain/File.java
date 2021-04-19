package cn.wx.ycloudtech.domain;

import com.baomidou.mybatisplus.annotations.TableId;

public class File {
    /** 主键ID;本表主键ID */
    @TableId
    private String fileId ;
    /** 关联ID */
    private String linkId ;
    /** 关联类型 */
    private String linkType ;
    /** 文件路径 */
    private String fileAddr ;
    /** 上传时间 */
    private String fileTime ;

    /** 主键ID;本表主键ID */
    public String getFileId(){
        return this.fileId;
    }
    /** 主键ID;本表主键ID */
    public void setFileId(String fileId){
        this.fileId = fileId;
    }
    /** 关联ID */
    public String getLinkId(){
        return this.linkId;
    }
    /** 关联ID */
    public void setLinkId(String linkId){
        this.linkId = linkId;
    }
    /** 关联类型 */
    public String getLinkType(){
        return this.linkType;
    }
    /** 关联类型 */
    public void setLinkType(String linkType){
        this.linkType = linkType;
    }
    /** 文件路径 */
    public String getFileAddr(){
        return this.fileAddr;
    }
    /** 文件路径 */
    public void setFileAddr(String fileAddr){
        this.fileAddr = fileAddr;
    }
    /** 上传时间 */
    public String getFileTime(){
        return this.fileTime;
    }
    /** 上传时间 */
    public void setFileTime(String fileTime){
        this.fileTime = fileTime;
    }
}
