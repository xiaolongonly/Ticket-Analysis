package cn.xiaolong.ticketsystem.ui.chartconfig;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import cn.xiaolong.ticketsystem.R;


/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date 2016/11/7-11:20
 */
public class DataMarkView extends MarkerView {
    protected TextView tvContent;
    //小数的位数
    private int digits;
    //单位
    private String unit;
    //乘以某个数
    private int multiply = 1;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public DataMarkView(Context context, int layoutResource) {
        super(context, layoutResource);
    }

    public DataMarkView(Context context, int digits, String unit) {
        this(context, R.layout.layout_markview);
        tvContent = (TextView) findViewById(R.id.tvContent);
        this.digits = digits;
        this.unit = unit;
    }

    public DataMarkView(Context context, int digits, int multiply, String unit) {
        this(context, digits, unit);
        this.multiply = multiply;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            tvContent.setText(Utils.formatNumber(ce.getHigh() * multiply, digits, true) + unit);
        } else {
            tvContent.setText(Utils.formatNumber(e.getY() * multiply, digits, true) + unit);
        }
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-tvContent.getWidth() / 2, -tvContent.getHeight());
    }
}
