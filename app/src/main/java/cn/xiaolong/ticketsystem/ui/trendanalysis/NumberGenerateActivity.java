package cn.xiaolong.ticketsystem.ui.trendanalysis;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.base.BaseTitleBar;
import cn.xiaolong.ticketsystem.base.BaseTitleBarActivity;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/19 17:29
 */

public class NumberGenerateActivity extends BaseTitleBarActivity {
    @Override
    public void initTitleBar(BaseTitleBar titleBar) {
        titleBar.setTitleText("随机摇号");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_number_generate;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void setListener() {

    }
}
