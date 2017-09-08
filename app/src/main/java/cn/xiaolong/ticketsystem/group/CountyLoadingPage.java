package cn.xiaolong.ticketsystem.group;

import android.content.Context;
import android.view.ViewGroup;

import com.standards.library.listview.loading.BaseLoadingPage;
import com.standards.library.listview.loading.OnFailClickListener;


/**
 * <加载页> 这边是列表的Loading页面
 *
 * @data: 2015/12/14 15:09
 * @version: V1.0
 */
public class CountyLoadingPage extends BaseLoadingPage {
    protected boolean mIsShowLaunchPage;

    protected OnFailClickListener mOnFailClickListener;

    private LoadingHelp mLoadingHelp;

    public CountyLoadingPage(Context context) {
        super(context);
    }

    @Override
    public void createLoadingPage(ViewGroup rootView) {
        mIsShowLaunchPage = false;
        mLoadingHelp = new LoadingHelp(rootView.getContext());
        rootView.addView(this, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


    @Override
    public boolean isShowLaunchPage() {
        return mIsShowLaunchPage;
    }


    @Override
    public void showLoading() {
        hideLoading();

        mLoadingHelp.initView(mContext);
        addView(mLoadingHelp.getRootView());
        mIsShowLaunchPage = true;
        mLoadingHelp.showLoading();
    }

    @Override
    public void showFailPage(int failCode) {

    }

    @Override
    public void setOnFailClickListener(OnFailClickListener onFailClickListener) {
        this.mOnFailClickListener = onFailClickListener;
        if (mLoadingHelp != null) {
            mLoadingHelp.setOnFailClickListener(onFailClickListener);
        }
    }

    @Override
    public void showEmptyLoadingPage() {
    }

    public LoadingHelp getLoadViewHelp() {
        return mLoadingHelp;
    }

    @Override
    public void hideLoading() {
        mIsShowLaunchPage = false;
        if (getChildCount() > 0) {
            removeAllViews();
        }
    }
}
