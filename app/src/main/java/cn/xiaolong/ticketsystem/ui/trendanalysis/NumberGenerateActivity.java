package cn.xiaolong.ticketsystem.ui.trendanalysis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.standards.library.app.AppContext;
import com.standards.library.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.base.BaseTitleBar;
import cn.xiaolong.ticketsystem.base.BaseTitleBarActivity;
import cn.xiaolong.ticketsystem.bean.TicketRegular;
import cn.xiaolong.ticketsystem.utils.LaunchUtil;
import cn.xiaolong.ticketsystem.utils.NumberGenerateHelper;
import cn.xiaolong.ticketsystem.utils.SpannableStringUtil;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/19 17:29
 */

public class NumberGenerateActivity extends BaseTitleBarActivity {
    public static final int REQUEST_NUMBER_BASE = 0X111;
    private AppCompatSpinner spGenerateCount;
    private TicketRegular mTicketRegular;
    private List<List<String>> numberBase;
    private TextView tvChooseNum;

    private NumberGenerateHelper numberGenerateHelper;

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
        titleBar.setTitleText("随机摇号");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_number_generate;
    }

    @Override
    protected void init() {
        spGenerateCount = findView(R.id.spGenerateCount);
        tvChooseNum = findView(R.id.tvChooseNum);
        SpinnerAdapter spinnerAdapter = new ArrayAdapter(this, R.layout.item_spinner_array,
                new String[]{"1 注", "2 注", "5 注", "10 注", "20 注", "50 注"});
        spGenerateCount.setAdapter(spinnerAdapter);
        numberGenerateHelper = new NumberGenerateHelper(mTicketRegular);
        numberGenerateHelper.generateNumberGroup(null);
    }

    @Override
    protected void setListener() {
        ClickView(findView(R.id.llNumberBase))
                .subscribe(o -> LaunchUtil.launchActivity(this, NumberBaseSelectActivity.class,
                        NumberBaseSelectActivity.buildBundle(mTicketRegular, numberBase), REQUEST_NUMBER_BASE));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NUMBER_BASE && resultCode == NumberBaseSelectActivity.RESULT_NUMBER_BASE) {
            numberBase = (List<List<String>>) data.getSerializableExtra("numberBase");
            int baseCount = Integer.valueOf(mTicketRegular.regular.split("\\+")[0]);
            int baseLength = 0;
            String result = "";
            for (List<String> stringList : numberBase) {
                if (stringList.size() == 0) {
                    result += "X ";
                }
                for (String str : stringList) {
                    result += (str.equals("") ? "X" : str) + " ";
                    baseCount--;
                    if (baseCount == 0) {
                        baseLength = result.length();
                    }
                }
            }
            SpannableString spannableString = new SpannableString(result);
            spannableString.setSpan(new ForegroundColorSpan(AppContext.getContext().getResources().getColor(R.color.main_blue_color_4c65ed)),
                    baseLength, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvChooseNum.setText(spannableString);
        }
    }


}
