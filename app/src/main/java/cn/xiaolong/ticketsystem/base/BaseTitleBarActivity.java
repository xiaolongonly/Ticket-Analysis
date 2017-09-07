package cn.xiaolong.ticketsystem.base;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.utils.StatusBarValue;


/**
 * 带titleBar的Activity抽象
 *
 * @param <T>
 */
public abstract class BaseTitleBarActivity<T extends BasePresenter> extends BaseFuncActivity<T> {

    @Override
    public void setContentView(View contentView) {
        ViewGroup superContentView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.activity_base, (ViewGroup) contentView, false);
        Toolbar toolbar = (Toolbar) superContentView.findViewById(R.id.toolbar);
        RelativeLayout content = (RelativeLayout) superContentView.findViewById(R.id.content);
        content.addView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        BaseTitleBar mTitleBar = new BaseTitleBar(this, toolbar, getDefaultTitleBarLayout());
        mTitleBar.setOnLeftClickListener(view -> onTitleLeftClick());
        initTitleBar(mTitleBar);
        super.setContentView(superContentView);
    }

    public void onTitleLeftClick() {
        finish();
    }

    public int getDefaultTitleBarLayout() {
        return R.layout.titlebar_normal;
    }

    public abstract void initTitleBar(BaseTitleBar titleBar);

    @Override
    public StatusBarValue getStatusBar() {
        return new StatusBarValue(true, R.color.main_red_color);
    }
}
