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
import cn.xiaolong.ticketsystem.utils.ArrayUtil;

/**
 * @author xiaolong
 * @version v1.0
 * @function <和值分析>
 * @date: 2017/9/14 14:48
 */

public class SumAnalysisActivity extends BaseTitleBarActivity<ParityTrendPresenter> implements IParityTrendView {
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
            tvTitle.setText(mTicketType.descr + "和值分析");
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
                lineDataSet.setValues(generateEntry(list));
            }
            lcParityTrend.getData().notifyDataChanged();
            lcParityTrend.notifyDataSetChanged();
        } else {
            dataSetList.add(LineChartHelper.
                    getsLineChartHelper().
                    generateLineDataSet(generateEntry(list),
                            Color.rgb(255, 0, 0),
                            "和值分布"));
            lineData = new LineData(dataSetList);
            lcParityTrend.setData(lineData);
            lcParityTrend.getXAxis().setValueFormatter((value, axis) -> {
                if (value >= 0 && list.size() > value)
                    return list.get((int) value).expect + "期";
                else
                    return "0";
            });
            lcParityTrend.getAxisLeft().setValueFormatter((value, axis) -> value + "");
            lcParityTrend.setMarker(new DataMarkView(this, new DataMarkView.IDataValueFormat() {
                @Override
                public String format(Entry e, Highlight highlight) {
                    if (e.getX() >= 0 && list.size() > e.getX())
                        return list.get((int) e.getX()).expect + "期：" + e.getY();
                    else
                        return "0";
                }
            }));
        }
        lcParityTrend.animateX(3000);
    }

    private List<Entry> generateEntry(List<TicketOpenData> list) {
        List<Entry> entryList = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            String first[] = translateCodeToList(list.get(j).openCode).first;
            String second[] = translateCodeToList(list.get(j).openCode).second;
            String[] values = ArrayUtil.concat(first, second);
            int count = 0;

            for (int i = 0; i < values.length; i++) {
                try {
                    count += Integer.valueOf(values[i]);
                } catch (Exception e) {
                    count += 0;
                }
            }
            entryList.add(new Entry(j, count));
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
