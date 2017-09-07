package cn.xiaolong.ticketsystem.utils;

/**
 * <状态栏 配置>
 *
 * @data: 2016/2/16 ic_select:45
 * @version: V1.0
 */
public class StatusBarValue {

    public boolean isStatusBarOccupying;

    public int statusBarColor;

    public StatusBarValue(boolean isStatusBarOccupying, int statusBarColor) {
        this.isStatusBarOccupying = isStatusBarOccupying;
        this.statusBarColor = statusBarColor;
    }
    
}
