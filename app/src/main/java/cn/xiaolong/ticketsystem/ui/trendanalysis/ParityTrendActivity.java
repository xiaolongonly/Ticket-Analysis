package cn.xiaolong.ticketsystem.ui.trendanalysis;

import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.List;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.base.BaseTitleBar;
import cn.xiaolong.ticketsystem.base.BaseTitleBarActivity;
import cn.xiaolong.ticketsystem.bean.TicketOpenData;
import cn.xiaolong.ticketsystem.bean.TicketType;
import cn.xiaolong.ticketsystem.presenter.ParityTrendPresenter;
import cn.xiaolong.ticketsystem.presenter.view.IParityTrendView;
import cn.xiaolong.ticketsystem.utils.LineChartHelper;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/14 14:48
 */

public class ParityTrendActivity extends BaseTitleBarActivity<ParityTrendPresenter> implements IParityTrendView {
    private TextView tvParityTrendAnalysis;
    private TicketType mTicketType;
    private TextView tvTitle;
    private LineChart lcParityTrend;

    @Override
    public void initTitleBar(BaseTitleBar titleBar) {
        tvTitle = (TextView) titleBar.center;
    }

    public static Bundle buildBundle(TicketType ticketType) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("ticketType", ticketType);
        return bundle;
    }

    @Override
    public void getExtra() {
        mTicketType = (TicketType) getIntent().getSerializableExtra("ticketType");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_parity_trend;
    }

    @Override
    public ParityTrendPresenter getPresenter() {
        return new ParityTrendPresenter(this);
    }

    @Override
    protected void init() {
        tvParityTrendAnalysis = findView(R.id.tvParityTrendAnalysis);
        lcParityTrend = findView(R.id.lcParityTrend);
        if (mTicketType != null) {
            tvTitle.setText(mTicketType.descr + "奇偶趋势");
            mPresenter.getRecentOpenDatas(mTicketType.code, "100");
        }
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onGetHistoryRecentTicketListSuccess(List<TicketOpenData> list) {
        lcParityTrend = LineChartHelper.getsLineChartHelper().generateLineChartConfig(lcParityTrend);
        if (lcParityTrend.getData() != null &&
                lcParityTrend.getData().getDataSetCount() > 0) {
            LineData lineData = lcParityTrend.getLineData();
            for (int i = 0; i < lineData.getDataSetCount(); i++) {

            }
//            set1.setValues(yVals1);
//            set2.setValues(yVals2);
//            set3.setValues(yVals3);
            lcParityTrend.getData().notifyDataChanged();
            lcParityTrend.notifyDataSetChanged();
        } else {
//            for(int i=0;i<list.clear();i++)
//            LineDataSet lineDataSet = LineChartHelper.getsLineChartHelper().generateLineDataSet(, getResources().getColor(R.color.main_red_color, ""));
        }
    }
}
