package cn.xiaolong.ticketsystem.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.standards.library.listview.ListGroupPresenter;
import com.standards.library.listview.listview.RecycleListViewImpl;
import com.standards.library.listview.manager.BaseGroupListManager;
import com.standards.library.util.LogUtil;
import com.standards.library.util.ToastUtil;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.adapter.TicketTypeAdapter;
import cn.xiaolong.ticketsystem.base.BaseTitleBar;
import cn.xiaolong.ticketsystem.base.BaseTitleBarActivity;
import cn.xiaolong.ticketsystem.bean.TicketType;
import cn.xiaolong.ticketsystem.group.LoadingPage;
import cn.xiaolong.ticketsystem.group.Scene;
import cn.xiaolong.ticketsystem.manager.TicketTypeManager;
import cn.xiaolong.ticketsystem.ui.widget.RecycleViewDivider;
import cn.xiaolong.ticketsystem.utils.LaunchUtil;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/7 14:45
 */

public class MainActivity extends BaseTitleBarActivity {
    private TicketTypeAdapter ticketTypeAdapter;
    private ListGroupPresenter presenter;
    private BaseGroupListManager manager;
    private RecycleListViewImpl recycleListView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

        recycleListView = new RecycleListViewImpl(true, false, false);
        RelativeLayout rlContent = findView(R.id.rlContent);
        LoadingPage loadingPage = new LoadingPage(this, Scene.DEFAULT);
        ticketTypeAdapter = new TicketTypeAdapter(this);

        manager = new TicketTypeManager();
        presenter = ListGroupPresenter.create(this, recycleListView, manager, ticketTypeAdapter, loadingPage);
        recycleListView.getRecyclerView().addItemDecoration(new RecycleViewDivider(this,
                LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.main_black_color_999999)));
        rlContent.addView(presenter.getRootView(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected void setListener() {
        ticketTypeAdapter.setOnItemClickListener(view ->
                LaunchUtil.launchActivity(this, OpenResultActivity.class,
                        OpenResultActivity.buildBundle((TicketType) view.getTag())));
    }

    @Override
    public void initTitleBar(BaseTitleBar titleBar) {
        titleBar.setTitleText("彩票列表");
    }
}
