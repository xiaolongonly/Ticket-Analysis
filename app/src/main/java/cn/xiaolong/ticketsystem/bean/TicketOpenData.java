package cn.xiaolong.ticketsystem.bean;

import com.google.gson.annotations.SerializedName;
import com.standards.library.model.BaseInfo;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/8 11:06
 */

public class TicketOpenData extends BaseInfo {
    @SerializedName("timestamp")
    public Long timestamp; //开奖时间戳
    @SerializedName("name")
    public String name; //彩票名称
    @SerializedName("openCode")
    public String openCode;//中奖号码
    @SerializedName("time")
    public String time;//开奖时间字符串
    @SerializedName("expect")
    public String expect;//开奖编号 //期号
    @SerializedName("code")
    public String code; //彩票类型编码
}
