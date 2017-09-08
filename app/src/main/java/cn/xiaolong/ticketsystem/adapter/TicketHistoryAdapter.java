package cn.xiaolong.ticketsystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.standards.library.listview.adapter.LoadMoreRecycleAdapter;
import com.standards.library.util.DateUtils;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.bean.TicketOpenData;

/**
 * <请描述这个类是干什么的>
 *
 * @data: 2016/8/9 11:18
 * @version: V1.0
 */
public class TicketHistoryAdapter extends LoadMoreRecycleAdapter<TicketOpenData, TicketHistoryAdapter.ViewHolder> {

    public TicketHistoryAdapter(Context mContext) {
        super(mContext);
        removeHeaderView(0X666);
        removeFooterView(0X11);
    }


    @Override
    public ViewHolder onCreateContentView(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_ticket_history, parent, false));
    }

    @Override
    public void onBindView(ViewHolder viewHolder, int realPosition) {
        viewHolder.setData(mData.get(realPosition), realPosition);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOpenSerial;
        private TextView tvOpenTime;
        private TextView tvOpenResult;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOpenSerial = (TextView) itemView.findViewById(R.id.tvOpenSerial);
            tvOpenTime = (TextView) itemView.findViewById(R.id.tvOpenTime);
            tvOpenResult = (TextView) itemView.findViewById(R.id.tvOpenResult);
        }

        public void setData(TicketOpenData ticketOpenData, int realPosition) {
            tvOpenResult.setText(ticketOpenData.openCode);
            tvOpenTime.setText("开奖日期：" + DateUtils.timeStampToYYDDMM(ticketOpenData.timestamp * 1000));
            tvOpenSerial.setText("第" + ticketOpenData.expect + "期");

            itemView.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    v.setTag(ticketOpenData);
                    mOnItemClickListener.onClick(v);
                }
            });

        }
    }
}
