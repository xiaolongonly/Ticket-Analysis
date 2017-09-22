package cn.xiaolong.ticketsystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.xiaolong.ticketsystem.R;


/**
 * @author xiaolong
 * @version v1.0
 * @function <号码适配器，给联动搞吐血！！>
 * @date: 2017/9/13 10:33
 */

public class CodeForChooseAdapter extends RecyclerView.Adapter<CodeForChooseAdapter.ViewHolder> {

    private Context mContext;

    private List<String> mCodeList;

    private Boolean special;

    private int forceSize;
    private List<String> mSelectedCodes;
    private int mPosition;
    private List<List<String>> mSelectedList;
    private View.OnClickListener mOnClickListener;

    public CodeForChooseAdapter(Context context, List<String> codeList, boolean isSpecial, int size, int position, List<List<String>> selectedList) {
        this(context, codeList, isSpecial, size, selectedList.get(position));
        this.mPosition = position;
        this.mSelectedList = selectedList;
    }

    public CodeForChooseAdapter(Context context, List<String> numberList, boolean isSpecial, int size, List<String> selectedCodes) {
        mContext = context;
        mCodeList = numberList;
        special = isSpecial;
        forceSize = size;
        mSelectedCodes = selectedCodes;
        for (int i = selectedCodes.size(); i < forceSize; i++) {
            mSelectedCodes.add("");
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CodeForChooseAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_code, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mCodeList.get(position));
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
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
            if (getValidNumCount(mSelectedCodes) >= forceSize) {
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
            if (mSelectedList != null) {
                for (int i = 0; i < mSelectedList.size(); i++) {
                    for (int j = 0; j < mSelectedList.get(i).size(); j++) {
                        if (i != mPosition && data.equals(mSelectedList.get(i).get(j))) {
                            tvCode.setEnabled(false);
                        }
                    }
                }
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
                    mSelectedCodes.set(mSelectedCodes.indexOf(data), "");
                    if (mOnClickListener != null) {
                        mOnClickListener.onClick(v);
                    }
                    notifyDataSetChanged();
                } else {
                    v.setSelected(true);
                    mSelectedCodes.set(findEmptyIndex(mSelectedCodes), data);
                    if (mOnClickListener != null) {
                        mOnClickListener.onClick(v);
                    }
                    notifyDataSetChanged();
                }
            });
        }

        private int findEmptyIndex(List<String> mSelectedCodes) {
            int position = 0;
            for (int i = 0; i < mSelectedCodes.size(); i++) {
                if (TextUtils.isEmpty(mSelectedCodes.get(i))) {
                    position = i;
                    break;
                }
            }
            return position;
        }

        private int getValidNumCount(List<String> mSelectedCodes) {
            int count = 0;
            for (String str : mSelectedCodes) {
                if (!TextUtils.isEmpty(str)) {
                    count++;
                }
            }
            return count;
        }
    }
}
