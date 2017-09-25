package cn.xiaolong.ticketsystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.xiaolong.ticketsystem.R;

/**
 * <请描述这个类是干什么的>
 *
 * @data: 2016/8/9 11:18
 * @version: V1.0
 */
public class CodeListAdapter extends RecyclerView.Adapter<CodeListAdapter.ViewHolder> {
    private Context mContext;
    private List<Integer> mNumbers;
//    private View.OnClickListener onDeleteClickListener;
//    private View.OnClickListener onTextClickListener;

    public CodeListAdapter(Context context, List<Integer> numbers) {
        mContext = context;
        mNumbers = numbers;
    }
//
//    public void setOnDeleteClickListener(View.OnClickListener onDeleteClickListener) {
//        this.onDeleteClickListener = onDeleteClickListener;
//    }
//
//    public void setOnTextClickListener(View.OnClickListener onTextClickListener) {
//        this.onTextClickListener = onTextClickListener;
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_number, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mNumbers.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mNumbers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFoodName;
        private ImageView ivDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFoodName = (TextView) itemView.findViewById(R.id.tvFoodName);
            ivDelete = (ImageView) itemView.findViewById(R.id.ivDelete);
        }

        public void setData(final int data, final int position) {
            tvFoodName.setText(data + "");
            tvFoodName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, data + "", Toast.LENGTH_LONG).show();
                }
            });
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mNumbers.contains(data)) {
                        mNumbers.remove(position);
                        notifyItemRemoved(position);
                    }

                }
            });
        }
    }


}
