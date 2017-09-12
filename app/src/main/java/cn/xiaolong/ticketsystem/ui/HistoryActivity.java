package cn.xiaolong.ticketsystem.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.standards.library.listview.ListGroupPresenter;
import com.standards.library.listview.listview.RecycleListViewImpl;
import com.standards.library.listview.manager.BaseGroupListManager;
import com.standards.library.model.Event;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.adapter.TicketHistoryAdapter;
import cn.xiaolong.ticketsystem.adapter.TicketTypeAdapter;
import cn.xiaolong.ticketsystem.base.BaseTitleBar;
import cn.xiaolong.ticketsystem.base.BaseTitleBarActivity;
import cn.xiaolong.ticketsystem.bean.TicketType;
import cn.xiaolong.ticketsystem.group.LoadingPage;
import cn.xiaolong.ticketsystem.group.Scene;
import cn.xiaolong.ticketsystem.manager.HistoryManager;
import cn.xiaolong.ticketsystem.manager.TicketTypeManager;
import cn.xiaolong.ticketsystem.ui.widget.RecycleViewDivider;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/8 15:25
 */

public class HistoryActivity extends BaseTitleBarActivity {

    private TicketType mTicketType;
    private TextView tvTitle;

    private TicketHistoryAdapter ticketHistoryAdapter;
    private ListGroupPresenter presenter;
    private BaseGroupListManager manager;
    private RecycleListViewImpl recycleListView;

    public static Bundle buildBundle(TicketType ticketType) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("ticketType", ticketType);
        return bundle;
    }

    @Override
    public void getExtra() {
        super.getExtra();
        mTicketType = (TicketType) getIntent().getSerializableExtra("ticketType");
    }

    @Override
    public void initTitleBar(BaseTitleBar titleBar) {
        tvTitle = (TextView) titleBar.center;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    protected void init() {
        if (mTicketType != null) {
            tvTitle.setText(mTicketType.descr);
            recycleListView = new RecycleListViewImpl(true, true, false);
            RelativeLayout rlContent = findView(R.id.rlContent);
            LoadingPage loadingPage = new LoadingPage(this, Scene.DEFAULT);
            ticketHistoryAdapter = new TicketHistoryAdapter(this);
            manager = new HistoryManager(mTicketType.code);
            presenter = ListGroupPresenter.create(this, recycleListView, manager, ticketHistoryAdapter, loadingPage);
            recycleListView.getRecyclerView().addItemDecoration(new RecycleViewDivider(this,
                    LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.main_black_color_999999)));
            rlContent.addView(presenter.getRootView(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    @Override
    protected void setListener() {

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
