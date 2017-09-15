package cn.xiaolong.ticketsystem.ui.chartconfig;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.standards.library.app.AppContext;

import java.util.ArrayList;
import java.util.List;

import cn.xiaolong.ticketsystem.manager.TicketTypeDataManager;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/15 14:55
 */

public class BarChartHelper {


    private static BarChartHelper sBarChartHelper;

    public static BarChartHelper getBarChartHelper() {
        if (sBarChartHelper == null) {
            synchronized (BarChartHelper.class) {
                if (sBarChartHelper == null) {
                    sBarChartHelper = new BarChartHelper();
                }
            }
        }
        return sBarChartHelper;
    }

    public IBarDataSet generateBarDataSet(List<BarEntry> entries, String describle, List<Integer> colors) {
        BarDataSet dataSet = new BarDataSet(entries, describle);
        dataSet.setColors(colors);
        dataSet.setValueTextSize(11f);
        int highLightColor = 0x20ffffff;
        dataSet.setHighLightColor(highLightColor);
        return dataSet;
    }

    public BarChart generateBarChartConfig(BarChart barChart) {
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //定制X轴是在图表上方还是下方。
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1);//放大的时候X值不增多
        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);
        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0);

        barChart.setMarker(new DataMarkView(AppContext.getContext(), 2, ""));
        barChart.setDescription(null);
        barChart.setNoDataText("无数据");
        barChart.getLegend().setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        //设置单方向和双方向缩放 true x,y方向可以同时控制，false只能控制x方向的缩小放大或者Y方向的缩小放大
        barChart.setPinchZoom(true);
        return barChart;
    }
}
