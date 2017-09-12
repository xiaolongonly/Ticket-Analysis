package cn.xiaolong.ticketsystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.bean.TicketType;
import cn.xiaolong.ticketsystem.ui.follow.ItemTouchHelperCallback;

/**
 * <请描述这个类是干什么的>
 * GU
 *
 * @data: 2016/8/9 11:18
 * @version: V1.0
 */
public class TicketTypeSortAdapter extends RecyclerView.Adapter<TicketTypeSortAdapter.ViewHolder> implements ItemTouchHelperCallback.OnItemPositionChangeListener {
    private Context mContext;
    private List<TicketType> mTicketTypeList;

    public TicketTypeSortAdapter(Context context, List<TicketType> ticketTypeList) {
        mContext = context;
        mTicketTypeList = ticketTypeList;
    }

    @Override
    public TicketTypeSortAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_ticket_type_follow_sort, parent, false));
    }

    @Override
    public void onBindViewHolder(TicketTypeSortAdapter.ViewHolder holder, int position) {
        holder.setData(mTicketTypeList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mTicketTypeList.size();
    }

    @Override
    public boolean onItemMove(int fromPos, int toPos) {
        TicketType ticketType = mTicketTypeList.get(fromPos);
        mTicketTypeList.remove(fromPos);
        mTicketTypeList.add(toPos, ticketType);
        notifyItemMoved(fromPos, toPos);
        return false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTicketName;
        private TextView tvFollow;
        private RelativeLayout rlFollow;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTicketName = (TextView) itemView.findViewById(R.id.tvTicketName);
            tvFollow = (TextView) itemView.findViewById(R.id.tvFollow);
            rlFollow = (RelativeLayout) itemView.findViewById(R.id.rlFollow);
        }

        public void setData(TicketType typeInfo, int realPosition) {
            tvTicketName.setText(typeInfo.descr);
            rlFollow.setSelected(true);
            tvFollow.setText("已关注");
            rlFollow.setOnClickListener(v -> {
                if (mTicketTypeList.contains(typeInfo)) {
                    int position = mTicketTypeList.indexOf(typeInfo);
                    mTicketTypeList.remove(typeInfo);
                    notifyItemRemoved(position);
                }
            });

        }
    }


}
