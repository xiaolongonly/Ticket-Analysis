package cn.xiaolong.ticketsystem.bean;

import com.google.gson.annotations.SerializedName;
import com.standards.library.model.BaseInfo;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/7 18:02
 */

public class TicketType extends BaseInfo {
    @SerializedName("series")
    public String series;
    @SerializedName("area")
    public String area;
    @SerializedName("issuer")
    public String issuer;
    @SerializedName("times")
    public String times;
    @SerializedName("hots")
    public String hots;
    @SerializedName("high")
    public String high;
    @SerializedName("code")
    public String code;
    @SerializedName("notes")
    public String notes;
    @SerializedName("descr")
    public String descr;
}
