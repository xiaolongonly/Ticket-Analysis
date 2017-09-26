package cn.xiaolong.ticketsystem.ui.chartconfig;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.List;

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

    public IBarDataSet generateBarDataSet(List<BarEntry> entries, String[] describle, List<Integer> colors) {
        BarDataSet dataSet = new BarDataSet(entries, describle.length > 1 ? "" : describle[0]);
        dataSet.setColors(colors);
        dataSet.setStackLabels(describle);
//        dataSet.setValueTextSize(11f);
        int highLightColor = 0x20ffffff;
        dataSet.setHighLightColor(highLightColor);
        return dataSet;
    }

    public BarChart generateBarChartConfig(BarChart barChart) {
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //定制X轴是在图表上方还是下方。
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);//放大的时候X值不增多


        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);
        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0);

        barChart.setDrawBarShadow(false);
        barChart.setPinchZoom(true);
        barChart.setFitBars(true);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChart.setMaxVisibleValueCount(60);

        barChart.setDrawValueAboveBar(true);

        barChart.getDescription().setEnabled(false);
        barChart.setNoDataText("无数据");

        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //设置单方向和双方向缩放 true x,y方向可以同时控制，false只能控制x方向的缩小放大或者Y方向的缩小放大
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);
        return barChart;
    }
}
