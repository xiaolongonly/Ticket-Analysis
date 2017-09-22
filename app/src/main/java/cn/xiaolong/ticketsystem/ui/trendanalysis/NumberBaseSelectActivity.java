package cn.xiaolong.ticketsystem.ui.trendanalysis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.adapter.NumberBaseAdapter;
import cn.xiaolong.ticketsystem.adapter.NumberListAdapter;
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
    public static final int RESULT_NUMBER_BASE = 0x123456;
    private TicketRegular mTicketRegular;
    private RecyclerView rvBaseList;
    private TextView tvClear;
    private TextView tvConfirm;
    private NumberBaseAdapter numberBaseAdapter;
    private List<List<String>> numberBaseList;

    public static Bundle buildBundle(TicketRegular ticketRegular, List<List<String>> numberBase) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("ticketRegular", ticketRegular);
        bundle.putSerializable("numberBase", (Serializable) numberBase);
        return bundle;
    }

    @Override
    public void getExtra() {
        super.getExtra();
        mTicketRegular = (TicketRegular) getIntent().getSerializableExtra("ticketRegular");
        numberBaseList = (List<List<String>>) getIntent().getSerializableExtra("numberBase");
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
        numberBaseAdapter = new NumberBaseAdapter(this, mTicketRegular, numberBaseList);
        rvBaseList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvBaseList.setAdapter(numberBaseAdapter);
        tvClear = findView(R.id.tvClear);
        tvConfirm = findView(R.id.tvConfirm);

    }

    @Override
    protected void setListener() {
        tvClear.setOnClickListener(v -> numberBaseAdapter.getSelectedList().clear());
        tvConfirm.setOnClickListener(v -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("numberBase", (Serializable) numberBaseAdapter.getSelectedList());
            intent.putExtras(bundle);
            setResult(RESULT_NUMBER_BASE, intent);
            finish();
        });
    }

}
