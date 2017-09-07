package com.standards.library.listview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter implements IGroupAdapter<T> {
    public static final int INT_TYPE_HEADER = 0x99;
    public static final int INT_TYPE_FOOTER = 0x88;
    private static final String TAG = RecyclerAdapter.class.getSimpleName();
    protected Context mContext;
    protected List<T> mData;

    private final List<ExtraItem> headers;
    private final List<ExtraItem> footers;

    public static class ExtraItem<V extends RecyclerView.ViewHolder> {
        public final int type;
        public final V view;

        public ExtraItem(int type, V view) {
            this.type = type;
            this.view = view;
        }

    }

    public RecyclerAdapter(Context mContext) {
        this.mContext = mContext;
        this.headers = new ArrayList<>();
        this.footers = new ArrayList<>();
        mData = new ArrayList<T>();
    }

    public List<T> getData() {
        return mData;
    }

    public int getCount() {
        return mData == null ? 0 : mData.size();
    }


    @Override
    public int getItemCount() {
        int count = headers.size();
        count += getCount();
        count += footers.size();
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < headers.size()) {
            return headers.get(position).type;
        } else {
            if (position >= (getCount() + headers.size())) {
                return footers.get(position - (getCount() + headers.size())).type;
            } else {
                return getViewType(position, position - headers.size());
            }
        }
    }

    public int getViewType(int position, int dataRealPosition) {
        return super.getItemViewType(position);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        for (ExtraItem<VH> item : headers) {
            if (item.type == viewType) {
                return item.view;
            }
        }

        for (ExtraItem<VH> item : footers) {
            if (item.type == viewType) {
                return item.view;
            }
        }

        return onCreateContentView(parent, viewType);
    }

    public abstract VH onCreateContentView(ViewGroup parent, int viewType);


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position >= headers.size() && position < (getItemCount() - footers.size())) {
            onBindView((VH) holder, position - headers.size());
        } else if (position < headers.size()) {
            try {
//                final StaggeredGridLayoutManager.LayoutParams lp =
//                        (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
////
//                lp.setFullSpan(true);
//                ;
//
//                holder.itemView.setLayoutParams(lp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
//            LogUtil.d(TAG, "footer");
        }
    }

    public abstract void onBindView(VH viewHolder, int realPosition);

    public void addHeaderView(int type, VH headerView) {
        ExtraItem item = new ExtraItem(type, headerView);
        headers.add(item);
        notifyItemInserted(headers.size());
    }

    public void removeHeaderView(int type) {
        List<ExtraItem> indexesToRemove = new ArrayList<ExtraItem>();
        for (int i = 0; i < headers.size(); i++) {
            ExtraItem item = headers.get(i);
            if (item.type == type)
                indexesToRemove.add(item);
        }

        for (ExtraItem item : indexesToRemove) {
            int index = headers.indexOf(item);
            headers.remove(item);
            notifyItemRemoved(index);
        }
    }

    public void removeHeaderView(ExtraItem headerView) {
        int indexToRemove = headers.indexOf(headerView);
        if (indexToRemove >= 0) {
            headers.remove(indexToRemove);
            notifyItemRemoved(indexToRemove);
        }
    }

    public void addFooterView(int type, VH footerView) {
        ExtraItem item = new ExtraItem(type, footerView);
        footers.add(item);
        notifyItemInserted(getItemCount());
    }


    public void removeFooterView(int type) {
        List<ExtraItem> indexesToRemove = new ArrayList<ExtraItem>();
        for (int i = 0; i < footers.size(); i++) {
            ExtraItem item = footers.get(i);
            if (item.type == type)
                indexesToRemove.add(item);
        }

        for (ExtraItem item : indexesToRemove) {
            int index = footers.indexOf(item);
            footers.remove(item);
            notifyItemRemoved(headers.size() + getCount() + index);
        }
    }

    public void removeFooterView(ExtraItem footerView) {
        int indexToRemove = footers.indexOf(footerView);
        if (indexToRemove >= 0) {
            footers.remove(indexToRemove);
            notifyItemRemoved(headers.size() + getCount() + indexToRemove);
        }
    }

    public ExtraItem getHeader(int mIntArgHeaderPos) {

        if (headers != null && headers.size() > mIntArgHeaderPos) {
            return headers.get(mIntArgHeaderPos);
        }
        return null;
    }

    public ExtraItem getFooter(int position) {
        if (footers != null && footers.size() > position) {
            return footers.get(position);
        }
        return null;
    }

    @Override
    public void setItems(List<T> list) {
        if (mData == null) {
            mData = new ArrayList<T>();
        }
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void addItems(List<T> list) {
        if (mData == null) mData = new ArrayList<>();

        mData.addAll(list);
        notifyDataSetChanged();
    }

    private int getRealPosition(int dataPosition) {
        return dataPosition + headers.size();
    }

    @Override
    public void addItem(int position, T item) {
        if (position > mData.size()) {
            return;
        }
        mData.add(position, item);
        notifyItemInserted(getRealPosition(position));

        if (position != mData.size() - 1) {
            notifyItemRangeChanged(getRealPosition(position), mData.size() - position);
        }
    }

    /**
     * 删除指定位置的数据
     *
     * @param position
     */
    @Override
    public void removeItem(int position) {
        if (position >= mData.size()) {
            return;
        }
        mData.remove(position);
        notifyItemRemoved(getRealPosition(position));

        // 加入如下代码保证position的位置正确性
        if (position != mData.size() - 1) {
            notifyItemRangeChanged(getRealPosition(position), mData.size() - position);
        }
    }


    @Override
    public void removeAllItem() {
        if (mData == null || mData.size() == 0) {
            return;
        }
        mData.clear();
//        notifyItemRangeRemoved(0, mData.size()-1);
        notifyDataSetChanged();
    }


    @Override
    public void replaceItem(int position, T t) {
        if (mData.size() - 1 < position) {
            return;
        }
//        mData.remove(position);
//        mData.add(position, t);
        mData.set(position, t);
        notifyItemChanged(getRealPosition(position));

        // 加入如下代码保证position的位置正确性
        if (position != mData.size() - 1) {
            notifyItemRangeChanged(getRealPosition(position), mData.size() - position);
        }
    }

    @Override
    public void removeItemRange(int startPos, int count) {
        if (mData == null || mData.size() <= startPos || mData.size() < count + startPos) {
            return;
        }

        List<T> deleteList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            deleteList.add(mData.get(startPos + i));
        }
        mData.removeAll(deleteList);

        notifyItemRangeRemoved(getRealPosition(startPos), count);
    }

    @Override
    public int getAllItemCount() {
        return getItemCount();
    }

    @Override
    public int getDataCount() {
        return getCount();
    }

    @Override
    public void noMoreDataCallback() {
        //数据已经全部加载完毕 加载针对这三个回调做不同的响应
    }

    @Override
    public void onLoadMoreStart() {
        //开始加载
    }

    @Override
    public void onLoadMoreReset() {
        //重置loadmore数据回调 //page pagesize等。
    }

    @Override
    public void onLoadMoreFailData(int failCode) {
        //加载失败
    }
    public View.OnClickListener mOnItemClickListener;
    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        mOnItemClickListener = onClickListener;
    }
}
