package cn.wx.ycloudtech.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class MyConstants {
    // 基本返回
    public static final String RESULT_OK = "ok";
    public static final String RESULT_FAIL = "fail";

    //时间、数据格式
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_TIME = "HH:mm:ss";

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(MyConstants.FORMAT_DATE);
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(MyConstants.FORMAT_TIME);
    public static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat(MyConstants.FORMAT_DATETIME);

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("00%");

    // 特殊意思的返回
    public static final String RESULT_INSERT_FAIL = "insertFail";
    public static final String RESULT_UPDATE_FAIL = "updateFail";
    public static final String RESULT_DELETE_FAIL = "deleteFail";

    // 运维人员身份相关
    public static final String USER_SUPER_YES = "1";
    public static final String USER_SUPER_NOT = "0";

    // 招募者身份相关
    public static final String USER_ORGAN_YES = "1";
    public static final String USER_ORGAN_NOT = "0";

    // 组织机构申请开通状态
    public static final String ORGAN_SUBMIT = "0";
    public static final String ORGAN_PASS = "1";
    public static final String ORGAN_DENY = "2";

    // 活动进行状态
    public static final String ACT_SUBMIT = "0";
    public static final String ACT_RECRUITING = "1";
    public static final String ACT_RECRUIT_SUCCESS = "2";
    public static final String ACT_RECRUIT_FAIL = "3";
    public static final String ACT_CANCELED = "4";
    public static final String ACT_DURING = "5";
    public static final String ACT_END = "6";

    // 用户-活动状态
    public static final String USER_ACT_APPLY = "0";
    public static final String USER_ACT_APPLY_FAIL = "1";
    public static final String USER_ACT_APPLY_SUCCESS = "2";
    public static final String USER_ACT_CHECK_NEED = "3";
    public static final String USER_ACT_CHECKED = "4";
    public static final String USER_ACT_END = "5";

    // 媒体文件关联类型
    public static final String FILE_LINK_ORGAN = "0";
    public static final String FILE_LINK_REVIEW = "1";
    public static final String FILE_LINK_ACT = "2";

    // 投诉状态
    public static final String REPORT_SUBMIT = "0";
    public static final String REPORT_SUCCESS = "1";
    public static final String REPORT_FAIL = "2";

    // Wx端要求的apply结果
    public static final Integer APPLY_SUCCESS = 0;
    public static final Integer APPLY_FAIL = 1;

}
