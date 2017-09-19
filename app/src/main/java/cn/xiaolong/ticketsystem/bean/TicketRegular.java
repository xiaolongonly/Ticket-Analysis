package cn.xiaolong.ticketsystem.bean;

import com.standards.library.model.BaseInfo;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/19 16:21
 */

public class TicketRegular extends BaseInfo {

    public String codeDis;  //号码范围
    public String regular; //选码规则
    public String openRegular; //开奖结果
    public boolean repeat; //开奖码是否重复
    public String code; //编号
    public String descr;//名称

}
