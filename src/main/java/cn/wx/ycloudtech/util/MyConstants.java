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

}
