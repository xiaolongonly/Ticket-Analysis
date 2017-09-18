package cn.xiaolong.ticketsystem.ui.chartconfig;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import cn.xiaolong.ticketsystem.R;


/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date 2016/11/7-11:20
 */
public class DataMarkView extends MarkerView {
    protected TextView tvContent;
    private IDataValueFormat iDataValueFormat;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public DataMarkView(Context context, int layoutResource) {
        super(context, layoutResource);
    }

    public DataMarkView(Context context, IDataValueFormat iDataValueFormat) {
        this(context, R.layout.layout_markview);
        this.iDataValueFormat = iDataValueFormat;
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
//        if (e instanceof CandleEntry) {
//            CandleEntry ce = (CandleEntry) e;
////            tvContent.setText(Utils.formatNumber(ce.getHigh() * multiply, digits, true) + unit);
//        } else {
//            tvContent.setText(e.getX() + unit + "：" + e.getY());
//        }
        tvContent.setText(iDataValueFormat.format(e, highlight));
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-tvContent.getWidth() / 2, -tvContent.getHeight());
    }

    public interface IDataValueFormat {
        String format(Entry e, Highlight highlight);
    }
}
