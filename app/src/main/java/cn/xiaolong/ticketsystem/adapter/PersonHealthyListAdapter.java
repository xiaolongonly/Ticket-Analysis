package cn.xiaolong.ticketsystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.standards.library.listview.adapter.LoadMoreRecycleAdapter;

import org.w3c.dom.TypeInfo;

import cn.xiaolong.ticketsystem.R;

/**
 * <请描述这个类是干什么的>
 *
 * @data: 2016/8/9 11:18
 * @version: V1.0
 */
public class PersonHealthyListAdapter extends LoadMoreRecycleAdapter<TypeInfo, PersonHealthyListAdapter.ViewHolder> {

    public PersonHealthyListAdapter(Context mContext) {
        super(mContext);
        removeHeaderView(0X666);
        removeFooterView(0X11);
    }


    @Override
    public ViewHolder onCreateContentView(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_person_healthy, parent, false));
    }

    @Override
    public void onBindView(ViewHolder viewHolder, int realPosition) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);

        }

    }


}
