package cn.xiaolong.ticketsystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.standards.library.listview.adapter.LoadMoreRecycleAdapter;


import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.bean.TicketType;

/**
 * <请描述这个类是干什么的>
 *
 * @data: 2016/8/9 11:18
 * @version: V1.0
 */
public class TicketTypeAdapter extends LoadMoreRecycleAdapter<TicketType, TicketTypeAdapter.ViewHolder> {

    public TicketTypeAdapter(Context mContext) {
        super(mContext);
        removeHeaderView(0X666);
        removeFooterView(0X11);
    }


    @Override
    public ViewHolder onCreateContentView(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_ticket_type, parent, false));
    }

    @Override
    public void onBindView(ViewHolder viewHolder, int realPosition) {
        viewHolder.setData(mData.get(realPosition), realPosition);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNode;
        private TextView tvTicketName;
        private TextView tvArea;
        private RelativeLayout rlArea;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNode = (TextView) itemView.findViewById(R.id.tvNote);
            tvTicketName = (TextView) itemView.findViewById(R.id.tvTicketName);
            tvArea = (TextView) itemView.findViewById(R.id.tvArea);
            rlArea = (RelativeLayout) itemView.findViewById(R.id.rlArea);
        }

        public void setData(TicketType typeInfo, int realPosition) {
            tvNode.setText(typeInfo.notes);
            tvTicketName.setText(typeInfo.descr);
            if (realPosition > 0 && mData.get(realPosition - 1).area.equals(typeInfo.area)) {
                rlArea.setVisibility(View.GONE);
            } else {
                rlArea.setVisibility(View.VISIBLE);
                tvArea.setText(typeInfo.area.equals("") ? "全国" : typeInfo.area);
            }

            itemView.findViewById(R.id.rlBottom).setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    v.setTag(typeInfo);
                    mOnItemClickListener.onClick(v);
                }
            });

        }
    }


}
