package cn.xiaolong.ticketsystem.ui.chartconfig;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.standards.library.app.AppContext;

import java.util.List;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.manager.TicketTypeDataManager;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/14 17:36
 */

public class LineChartHelper {

    private static LineChartHelper sLineChartHelper;

    public static LineChartHelper getsLineChartHelper() {
        if (sLineChartHelper == null) {
            synchronized (LineChartHelper.class) {
                if (sLineChartHelper == null) {
                    sLineChartHelper = new LineChartHelper();
                }
            }
        }
        return sLineChartHelper;
    }

    public ILineDataSet generateLineDataSet(List<Entry> yEntrys, int color, String label) {
        LineDataSet dataSet = new LineDataSet(yEntrys, label);
        dataSet.setLineWidth(2.0f);
        dataSet.setCircleRadius(3.5f);
        dataSet.setDrawCircleHole(true);//填充圆
//        dataSet.setDrawValues(true);
//        dataSet.setValueTextColor(color);
        dataSet.setValueTextSize(9f);
        dataSet.setHighlightLineWidth(2.0f);
//        dataSet.setDrawFilled(true);//区域颜色
        dataSet.setFillAlpha(51);
//        dataSet.setFillColor(color); //填充色
        dataSet.setHighLightColor(color); //选中十字线色
        dataSet.setColor(color); //线条颜色
        dataSet.setCircleColor(color); //圆点颜色
        dataSet.setCircleColorHole(Color.WHITE);
        dataSet.setCircleHoleRadius(2.0f);
        dataSet.setDrawValues(false);
        return dataSet;
    }


    public LineChart generateLineChartConfig(LineChart lineChart) {

        //XY轴配置
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //定制X轴是在图表上方还是下方。
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1);
        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);
        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0);
        yAxisLeft.setGranularity(1);

        //背景设置
        lineChart.setDrawGridBackground(false);//表格背景绘制
        lineChart.setBackgroundColor(AppContext.getContext().getResources().getColor(R.color.white));
        //Legend定制
        lineChart.getLegend().setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        lineChart.getLegend().setForm(Legend.LegendForm.CIRCLE);//Legend样式
        //图表描述
        lineChart.setDescription(null);
        // 设置无数据文本提示
        lineChart.setNoDataText("暂无数据");
        //设置单方向和双方向缩放 true x,y方向可以同时控制，false只能控制x方向的缩小放大或者Y方向的缩小放大
        lineChart.setPinchZoom(true);
        return lineChart;
    }


}
