package cn.xiaolong.ticketsystem.ui.trendanalysis;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.base.BaseTitleBar;
import cn.xiaolong.ticketsystem.base.BaseTitleBarActivity;
import cn.xiaolong.ticketsystem.bean.TicketOpenData;
import cn.xiaolong.ticketsystem.bean.TicketType;
import cn.xiaolong.ticketsystem.presenter.ParityTrendPresenter;
import cn.xiaolong.ticketsystem.presenter.view.IParityTrendView;
import cn.xiaolong.ticketsystem.ui.chartconfig.BarChartHelper;
import cn.xiaolong.ticketsystem.ui.chartconfig.DataMarkView;
import cn.xiaolong.ticketsystem.utils.ArrayUtil;

/**
 * @author xiaolong
 * @version v1.0
 * @function <均值分析>
 * @date: 2017/9/15 11:38
 */

public class AvgAnalysisActivity extends BaseTitleBarActivity<ParityTrendPresenter> implements IParityTrendView {
    private TextView tvAnalysisResult;
    private TicketType mTicketType;
    private TextView tvTitle;
    private BarChart bcAvgAnalysis;

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
    public ParityTrendPresenter getPresenter() {
        return new ParityTrendPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_avg_analysis;
    }

    @Override
    protected void init() {
        tvAnalysisResult = findView(R.id.tvAnalysisResult);
        bcAvgAnalysis = findView(R.id.bcAvgAnalysis);
        if (mTicketType != null) {
            tvTitle.setText(mTicketType.descr + "均值分析");
            mPresenter.getRecentOpenDatas(mTicketType.code, "100");
        }
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onGetHistoryRecentTicketListSuccess(List<TicketOpenData> list) {
        bcAvgAnalysis = BarChartHelper.getBarChartHelper().generateBarChartConfig(bcAvgAnalysis);
        BarData barData;
        if (bcAvgAnalysis.getData() != null &&
                bcAvgAnalysis.getData().getDataSetCount() > 0) {
            barData = bcAvgAnalysis.getBarData();
            for (int i = 0; i < barData.getDataSetCount(); i++) {
                BarDataSet barDataSet = (BarDataSet) barData.getDataSetByIndex(i);
                barDataSet.setValues(generateEntry(list, bcAvgAnalysis.getData().getDataSetCount()));
            }
            bcAvgAnalysis.getData().notifyDataChanged();
            bcAvgAnalysis.notifyDataSetChanged();
        } else {
            int codeLength = translateCodeToList(list.get(0).openCode).first.length + translateCodeToList(list.get(0).openCode).second.length + 1;
            int divide = 255 / codeLength;
            List<Integer> colorList = new ArrayList<>();
            for (int i = 0; i < codeLength; i++) {
                colorList.add(Color.rgb(255 - divide * i, 0, 0 + divide * i));
            }
            IBarDataSet barDataSet = BarChartHelper.getBarChartHelper().generateBarDataSet(generateEntry(list, codeLength), new String[]{"均值图"}, colorList);
            barData = new BarData(barDataSet);
            bcAvgAnalysis.setData(barData);
            bcAvgAnalysis.getXAxis().setValueFormatter((value, axis) -> {
                if (value == codeLength - 1) {
                    return "和平均";
                } else {
                    return (1 + (int) value) + "号";
                }
            });
//            bcAvgAnalysis.getAxisLeft().setValueFormatter((value, axis) -> (value));
            bcAvgAnalysis.setMarker(new DataMarkView(this, new DataMarkView.IDataValueFormat() {
                @Override
                public String format(Entry e, Highlight highlight) {
                    if (e.getX() == codeLength - 1) {
                        return "和平均：" + e.getY();
                    } else {
                        return (1 + (int) e.getX()) + "号：" + e.getY();
                    }
                }
            }));
        }
        bcAvgAnalysis.animateY(3000);

    }

    private List<BarEntry> generateEntry(List<TicketOpenData> list, int codeLength) {
        float[] valuesCount = new float[codeLength];
        for (int j = 0; j < list.size(); j++) {
            Pair<String[], String[]> sPair = translateCodeToList(list.get(j).openCode);
            String[] values = ArrayUtil.concat(sPair.first, sPair.second);
            for (int k = 0; k < codeLength; k++) {
                try {
                    valuesCount[k] += Float.valueOf(values[k]);//非数字直接抛异常
                } catch (Exception e) {
                    e.printStackTrace();
                    valuesCount[k] += 0;
                }
            }
        }
        List<BarEntry> barEntries = new ArrayList<>();
        float count = 0;
        for (int i = 0; i < codeLength - 1; i++) {
            count += valuesCount[i] / list.size();
            barEntries.add(new BarEntry(i, valuesCount[i] / list.size()));
        }
        barEntries.add(new BarEntry(codeLength - 1, count));
        return barEntries;
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
