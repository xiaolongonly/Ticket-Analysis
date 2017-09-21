package cn.xiaolong.ticketsystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.xiaolong.ticketsystem.R;


/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/13 10:33
 */

public class CodeForChooseAdapter extends RecyclerView.Adapter<CodeForChooseAdapter.ViewHolder> {
    private Context mContext;

    private List<String> mCodeList;

    private Boolean special;

    private int forceSize;
    private List<String> mSelectedCodes;

    public CodeForChooseAdapter(Context context, List<String> codeList, boolean isSpecial, int size, List<String> selectedCodes) {
        mContext = context;
        mCodeList = codeList;
        special = isSpecial;
        forceSize = size;
        mSelectedCodes = selectedCodes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CodeForChooseAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_code, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mCodeList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCodeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCode;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCode = (TextView) itemView;
        }

        public void setData(String data) {
            if (mSelectedCodes.size() >= forceSize) {
                tvCode.setEnabled(false);
            } else {
                tvCode.setEnabled(true);
            }
            if (mSelectedCodes.contains(data)) {
                tvCode.setSelected(true);
                tvCode.setEnabled(true);
            } else {
                tvCode.setSelected(false);
            }
            if (special) {
                tvCode.setBackground(mContext.getResources().getDrawable(R.drawable.select_code_bg_shape_blue));
            } else {
                tvCode.setBackground(mContext.getResources().getDrawable(R.drawable.select_code_bg_shape_red));
            }
            tvCode.setText(data);
            tvCode.setOnClickListener(v -> {
                if (v.isSelected()) {
                    v.setSelected(false);
                    mSelectedCodes.remove(data);
                    notifyDataSetChanged();
                } else {
                    v.setSelected(true);
                    mSelectedCodes.add(data);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
