package cn.xiaolong.ticketsystem.ui.trendanalysis;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.base.BaseTitleBar;
import cn.xiaolong.ticketsystem.base.BaseTitleBarActivity;
import cn.xiaolong.ticketsystem.bean.TicketOpenData;
import cn.xiaolong.ticketsystem.bean.TicketType;
import cn.xiaolong.ticketsystem.presenter.ParityTrendPresenter;
import cn.xiaolong.ticketsystem.presenter.view.IParityTrendView;
import cn.xiaolong.ticketsystem.ui.chartconfig.DataMarkView;
import cn.xiaolong.ticketsystem.ui.chartconfig.LineChartHelper;

/**
 * @author xiaolong
 * @version v1.0
 * @function <奇偶分析>
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
        LineData lineData;
        List<ILineDataSet> dataSetList = new ArrayList<>();
        if (lcParityTrend.getData() != null &&
                lcParityTrend.getData().getDataSetCount() > 0) {
            lineData = lcParityTrend.getLineData();
            for (int i = 0; i < lineData.getDataSetCount(); i++) {
                LineDataSet lineDataSet = (LineDataSet) lineData.getDataSetByIndex(i);
                lineDataSet.setValues(generateEntry(list, i));
            }
            lcParityTrend.getData().notifyDataChanged();
            lcParityTrend.notifyDataSetChanged();
        } else {
            int codeLength = translateCodeToList(list.get(0).openCode).first.length + translateCodeToList(list.get(0).openCode).second.length;
            int colorDivider = 255 / codeLength;
            for (int i = 0; i < codeLength; i++) {
                dataSetList.add(LineChartHelper.
                        getsLineChartHelper().
                        generateLineDataSet(generateEntry(list, i),
                                Color.rgb(255 - i * colorDivider, 0, 0 + i * colorDivider),
                                "号码" + (i + 1)));

            }
            lineData = new LineData(dataSetList);
            lcParityTrend.setData(lineData);
            lcParityTrend.getXAxis().setValueFormatter((value, axis) -> {
                if (value >= 0 && list.size() > value)
                    return list.get((int) value).expect + "期";
                else
                    return "0";
            });
            lcParityTrend.setMarker(new DataMarkView(this, (e, highlight) -> {
                if (e.getX() >= 0 && list.size() > e.getX())
                    return list.get((int) e.getX()).expect + "期：" + (e.getY() % 2 == 1 ? "奇" : "偶");
                else
                    return "0";

            }));
            lcParityTrend.getAxisLeft().setValueFormatter((value, axis) -> value % 2 == 1 ? "奇" : "偶");
        }
        lcParityTrend.animateX(3000);
    }

    private List<Entry> generateEntry(List<TicketOpenData> list, int i) {
        List<Entry> entryList = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            String first[] = translateCodeToList(list.get(j).openCode).first;
            String second[] = translateCodeToList(list.get(j).openCode).second;
            try {
                if (i < first.length) {
                    entryList.add(new Entry(j, Float.valueOf(first[i]) % 2 + (i * 2)));
                } else {
                    entryList.add(new Entry(j, Float.valueOf(second[i - first.length]) % 2 + (i * 2)));
                }
            } catch (Exception e) {
                e.printStackTrace();
                entryList.add(new Entry(j, 0 + (i * 2)));
            }

        }
        return entryList;
    }

    private Pair<String[], String[]> translateCodeToList(String openCode) {
        String[] splitString = openCode.split("\\+");
        String[] openNumbers = splitString[0].split(",");
        String[] specialNumbers = new String[]{};
        if (splitString.length > 1) {
            //说明是带特别码的彩种
            specialNumbers = splitString[1].split(",");
        }
        return new Pair<>(openNumbers, specialNumbers);
    }
}
