package cn.xiaolong.ticketsystem.bean;

import com.standards.library.model.BaseInfo;

import java.io.Serializable;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/19 16:21
 */

public class TicketRegular extends BaseInfo implements Serializable{
//    "special": "+ 鼠，牛，虎，兔，龙，蛇，马，羊，猴，鸡，狗，猪",
//            "codeDis": "0-9,1-12",
//            "regular": "6+1",
//            "repeat": true,
//            "code": "df6j1",
//            "descr": "东方6+1"
    public String codeDis;  //号码范围  有一些存在正选号码和特别号码，用,隔开
    public String special; //
    public String regular; //选码规则
    public String openRegular; //开奖结果
    public boolean repeat; //开奖码是否重复
    public String code; //编号
    public String descr;//名称

}
