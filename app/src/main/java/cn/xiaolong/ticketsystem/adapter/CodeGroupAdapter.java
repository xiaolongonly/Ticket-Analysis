package cn.xiaolong.ticketsystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.xiaolong.ticketsystem.R;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/13 10:33
 */

public class CodeGroupAdapter extends RecyclerView.Adapter<CodeGroupAdapter.ViewHolder> {
    public static final int SPECIAL_CODE = 1;
    public static final int NORMAL_CODE = 2;
    private Context mContext;
    /**
     * 因为只有一组中奖码和一组特别号码的情况，所以这边就直接写死了，
     * 有其他的可扩展实现，但是因为再怎么扩展，劳资也只有两种颜色 2333
     */
    private String[] openNumbers;
    private String[] specialNumbers;

    public CodeGroupAdapter(Context context, String openResult) {
        mContext = context;
        setupOpenDatas(openResult);
    }

    public void setData(String data) {
        //动态刷新，暂时用不到
        setupOpenDatas(data);
        notifyDataSetChanged();
    }

    private void setupOpenDatas(String openResult) {
        String[] splitString = openResult.split("\\+");
        openNumbers = splitString[0].split(",");
        if (splitString.length > 1) {
            //说明是带特别码的彩种
            specialNumbers = splitString[1].split(",");
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CodeGroupAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_code_small, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position < openNumbers.length) {
            holder.setData(openNumbers[position], NORMAL_CODE);
        } else {
            holder.setData(specialNumbers[position - openNumbers.length], SPECIAL_CODE);
        }
    }

    @Override
    public int getItemCount() {
        return openNumbers.length + (specialNumbers == null ? 0 : specialNumbers.length);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCode;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCode = (TextView) itemView;
        }

        public void setData(String data, int code) {
            if (code == 2) {
                tvCode.setSelected(false);
            } else {
                tvCode.setSelected(true);
            }
            tvCode.setText(data);
        }
    }
}
