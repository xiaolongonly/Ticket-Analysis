package cn.xiaolong.ticketsystem.adapter;

import android.content.Context;
import android.hardware.camera2.CameraDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.standards.library.listview.adapter.LoadMoreRecycleAdapter;

import org.w3c.dom.TypeInfo;

import java.util.ArrayList;
import java.util.List;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.bean.TicketType;

/**
 * <请描述这个类是干什么的>
 * GU
 * @data: 2016/8/9 11:18
 * @version: V1.0
 */
public class TicketTypeFollowAdapter extends LoadMoreRecycleAdapter<TicketType, TicketTypeFollowAdapter.ViewHolder> {
    private List<TicketType> mFollowedTicketTypes;

    public TicketTypeFollowAdapter(Context mContext, List<TicketType> followedTicketTypes) {
        super(mContext);
        removeHeaderView(0X666);
        removeFooterView(0X11);
        mFollowedTicketTypes = followedTicketTypes;
        if (mFollowedTicketTypes == null) {
            mFollowedTicketTypes = new ArrayList<>();
        }

    }


    @Override
    public ViewHolder onCreateContentView(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_ticket_type_follow, parent, false));
    }

    @Override
    public void onBindView(ViewHolder viewHolder, int realPosition) {
        viewHolder.setData(mData.get(realPosition), realPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlArea;
        private TextView tvArea;
        private TextView tvTicketName;
        private TextView tvFollow;
        private RelativeLayout rlFollow;

        public ViewHolder(View itemView) {
            super(itemView);
            rlArea = (RelativeLayout) itemView.findViewById(R.id.rlArea);
            tvArea = (TextView) itemView.findViewById(R.id.tvArea);
            tvTicketName = (TextView) itemView.findViewById(R.id.tvTicketName);
            tvFollow = (TextView) itemView.findViewById(R.id.tvFollow);
            rlFollow = (RelativeLayout) itemView.findViewById(R.id.rlFollow);
        }

        public void setData(TicketType typeInfo, int realPosition) {
            if (realPosition > 0 && mData.get(realPosition - 1).area.equals(typeInfo.area)) {
                rlArea.setVisibility(View.GONE);
            } else {
                rlArea.setVisibility(View.VISIBLE);
                tvArea.setText(typeInfo.area.equals("") ? "全国" : typeInfo.area);
            }
            tvTicketName.setText(typeInfo.descr);
            if (typeInList(typeInfo, mFollowedTicketTypes)) {
                rlFollow.setSelected(true);
                tvFollow.setText("已关注");
            } else {
                rlFollow.setSelected(false);
                tvFollow.setText("关注");
            }

            rlFollow.setOnClickListener(v ->
            {
                if (!rlFollow.isSelected()) {
                    mFollowedTicketTypes.add(typeInfo);
                } else {
                    for (TicketType followedType : mFollowedTicketTypes) {
                        if (typeInfo.code.equals(followedType.code)) {
                            mFollowedTicketTypes.remove(followedType);
                            break;
                        }
                    }
                }
                notifyItemChanged(realPosition);
            });
        }
    }

    private boolean typeInList(TicketType typeInfo, List<TicketType> mFollowedTicketTypes) {
        for (TicketType followTypeInfo : mFollowedTicketTypes) {
            if (typeInfo.code.equals(followTypeInfo.code)) {
                return true;
            }
        }
        return false;
    }


}
