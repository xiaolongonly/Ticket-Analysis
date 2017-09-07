package cn.xiaolong.ticketsystem.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.standards.library.app.AppContext;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/7/17 10:48
 */

public class SpannableStringUtil {

    public static SpannableString getStringWithColor(String str, int[] colorRes, int ...splitPosition) {
        SpannableString spannableString =new SpannableString(str);
        for(int i=0;i<splitPosition.length;i++)
        {
            if(i<splitPosition.length-1) {
                addSpannable(spannableString, colorRes[i],splitPosition[i],splitPosition[i+1]);
            }else
            {
                addSpannable(spannableString, colorRes[i],splitPosition[i],spannableString.length());
            }
        }
        return spannableString;
    }

    public static SpannableString addSpannable(SpannableString spannableString, int colorRes, int startPosition, int endPosition) {
        spannableString.setSpan(new ForegroundColorSpan(AppContext.getContext().getResources().getColor(colorRes)), startPosition, endPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
