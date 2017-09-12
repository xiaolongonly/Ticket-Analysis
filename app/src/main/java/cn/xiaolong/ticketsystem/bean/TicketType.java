package cn.xiaolong.ticketsystem.bean;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.standards.library.model.BaseInfo;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/7 18:02
 */

public class TicketType extends BaseInfo implements Comparable<TicketType> {
    @SerializedName("series")
    public String series; //彩票类型列表
    @SerializedName("area")
    public String area;//所属地区
    @SerializedName("issuer")
    public String issuer; //彩票分类
    @SerializedName("times")
    public String times; //每天开奖次数
    @SerializedName("hots")
    public String hots; //是否热门彩票
    @SerializedName("high")
    public String high; //是否高频彩票
    @SerializedName("code")
    public String code; //彩票的编号
    @SerializedName("notes")
    public String notes; //彩票描述
    @SerializedName("descr")
    public String descr; //彩票名字


    @Override
    public int compareTo(@NonNull TicketType o) {
        return area.compareTo(o.area);
    }
}
