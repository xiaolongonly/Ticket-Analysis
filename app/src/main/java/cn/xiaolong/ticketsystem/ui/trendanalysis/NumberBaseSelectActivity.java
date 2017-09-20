package cn.xiaolong.ticketsystem.ui.trendanalysis;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.base.BaseTitleBar;
import cn.xiaolong.ticketsystem.base.BaseTitleBarActivity;
import cn.xiaolong.ticketsystem.bean.TicketRegular;
import cn.xiaolong.ticketsystem.utils.LaunchUtil;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/19 17:29
 */

public class NumberBaseSelectActivity extends BaseTitleBarActivity {
    private AppCompatSpinner spGenerateCount;
    private TicketRegular mTicketRegular;
    private LinearLayout llNumChoose;
    private RecyclerView rvBaseList;

    public static Bundle buildBundle(TicketRegular ticketRegular) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("ticketRegular", ticketRegular);
        return bundle;
    }

    @Override
    public void getExtra() {
        super.getExtra();
        mTicketRegular = (TicketRegular) getIntent().getSerializableExtra("ticketRegular");
    }

    @Override
    public void initTitleBar(BaseTitleBar titleBar) {
        titleBar.setTitleText("胆码选择");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_number_base;
    }

    @Override
    protected void init() {
        rvBaseList = findView(R.id.rvBaseList);
    }

    @Override
    protected void setListener() {
    }
}
