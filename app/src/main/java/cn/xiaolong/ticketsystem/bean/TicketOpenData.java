package cn.xiaolong.ticketsystem.bean;

import com.standards.library.model.BaseInfo;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/8 11:06
 */

public class TicketOpenData extends BaseInfo{
    public Long timestamp; //开奖时间戳
    public String name; //彩票名称
    public String openCode;//中奖号码
    public String time;//开奖时间字符串
    public String expect;//开奖编号 //期号
    public String code; //彩票类型编码
}
