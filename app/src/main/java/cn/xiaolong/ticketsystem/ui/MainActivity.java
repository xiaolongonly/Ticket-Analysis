package cn.xiaolong.ticketsystem.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.adapter.TabFragmentAdapter;
import cn.xiaolong.ticketsystem.base.BaseFuncActivity;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/7 14:45
 */

public class MainActivity extends BaseFuncActivity {
    //    private TicketTypeAdapter ticketTypeAdapter;
//    private ListGroupPresenter presenter;
//    private BaseGroupListManager manager;
//    private RecycleListViewImpl recycleListView;
    private ViewPager pagerContent;
    private TabFragmentAdapter mTabFragmentAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        pagerContent = findView(R.id.pageContent);
        initTabTop(getFragmentTitle(), getFragmentList());

//        recycleListView = new RecycleListViewImpl(true, false, false);
//        RelativeLayout rlContent = findView(R.id.rlContent);
//        LoadingPage loadingPage = new LoadingPage(this, Scene.DEFAULT);
//        ticketTypeAdapter = new TicketTypeAdapter(this);
//
//        manager = new TicketTypeManager();
//        presenter = ListGroupPresenter.create(this, recycleListView, manager, ticketTypeAdapter, loadingPage);
//        recycleListView.getRecyclerView().addItemDecoration(new RecycleViewDivider(this,
//                LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.main_divider_color)));
//        rlContent.addView(presenter.getRootView(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private List<Fragment> getFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MyFollowFragment());
        fragmentList.add(new MyFollowFragment());
        fragmentList.add(new MyFollowFragment());
        fragmentList.add(new MyFollowFragment());
        return fragmentList;
    }

    private List<String> getFragmentTitle() {
        List<String> titles = new ArrayList<>();
        titles.add("关注");
        titles.add("地方");
        titles.add("全国");
        titles.add("境外");
        return titles;
    }

    private void initTabTop(List<String> fragmentTitles, List<Fragment> fragmentList) {
        mTabFragmentAdapter = new TabFragmentAdapter(fragmentList, fragmentTitles, getSupportFragmentManager(), this);
        pagerContent.setAdapter(mTabFragmentAdapter);
        TabLayout tlChannelTop = findView(R.id.tlChannelTop);
        tlChannelTop.setupWithViewPager(pagerContent);
    }

    @Override
    protected void setListener() {
//        ticketTypeAdapter.setOnItemClickListener(view ->
//                LaunchUtil.launchActivity(this, OpenResultActivity.class,
//                        OpenResultActivity.buildBundle((TicketType) view.getTag())));
    }

}
