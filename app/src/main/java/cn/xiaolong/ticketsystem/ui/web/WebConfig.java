package cn.xiaolong.ticketsystem.ui.web;


import com.standards.library.model.BaseInfo;

import java.io.Serializable;
/***
 * loadWithOver和layoutAlgorithm两种自适应屏幕方法
 * layoutAlgorithm解决了解海沧的适应问题
 */

/**
 * <//web页的配置信息>
 *
 * @data: 2016/6/22 16:09
 * @version: V1.0
 */
public class WebConfig extends BaseInfo implements Serializable {
    public String url;
    public boolean loadWithOver = true;
    public boolean isLayoutAlgorithm = false;
    public boolean isJump = true;
    public boolean isInterceptKeyEvent = true;//webView是否拦截按键事件

    public boolean leftClickFinishAble = true;//点击左上按钮是否立即退出activity
    public String title;//顶部标题
    public String publicParams;
    public String hostUrl;


    public WebConfig(String url, String title, boolean isJump) {
        this.url = url;
        this.title = title;
        this.isJump = isJump;
    }

    public void setLeftClickFinishAble(boolean leftClickFinishAble) {
        this.leftClickFinishAble = leftClickFinishAble;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setJump(boolean jump) {
        isJump = jump;
    }

    public void setLoadWithOver(boolean isOver) {
        this.loadWithOver = isOver;
    }

    public void setPublicParams(String publicParams) {
        this.publicParams = publicParams;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }
}
