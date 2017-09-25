package cn.xiaolong.ticketsystem.ui.trendanalysis;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.base.BaseTitleBar;
import cn.xiaolong.ticketsystem.base.BaseTitleBarActivity;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/25 15:24
 */

public class CodeForecastActivity extends BaseTitleBarActivity {
    @Override
    public void initTitleBar(BaseTitleBar titleBar) {
        titleBar.setTitleText("号码预测");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_code_forecast;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void setListener() {

    }
}
