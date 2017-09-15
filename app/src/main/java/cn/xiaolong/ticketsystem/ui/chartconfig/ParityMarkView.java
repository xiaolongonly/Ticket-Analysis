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
public class ParityMarkView extends MarkerView {
    protected TextView tvContent;


    public ParityMarkView(Context context) {
        super(context, R.layout.layout_markview);
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText(e.getY() % 2 == 1 ? "奇" : "偶");
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-tvContent.getWidth() / 2, -tvContent.getHeight());
    }
}
