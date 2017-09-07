package cn.xiaolong.ticketsystem.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.standards.library.activity.AutoLayoutActivity;
import com.standards.library.listview.loading.ProgressDialog;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import cn.xiaolong.ticketsystem.BuildConfig;
import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.utils.StatusBarCompat;
import cn.xiaolong.ticketsystem.utils.StatusBarValue;
import rx.Observable;


/**
 * <Activity的最高层抽象，主要如下
 * 1. 继承了autoLayoutActivity,用来实现不同的分辨率设备适配
 * 2. 实现了页面正在加载的dialog
 * 3. 实现了状态栏适配
 * 4. 对contentView和一些实现方法进一步封装>
 *
 * @data: 2016/6/7 17:30
 * @version: V1.0
 */
public abstract class BaseActivity extends AutoLayoutActivity {
    private View contentView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getExtra();
        handlerSaveInstanceState(savedInstanceState);
        contentView = LayoutInflater.from(this).inflate(getLayoutId(), null);
        setContentView(contentView);
        init();
        setListener();
    }

    @Override
    public void setContentView(View contentView) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout rootView = new LinearLayout(this);
        rootView.setOrientation(LinearLayout.VERTICAL);
        rootView.setLayoutParams(params);
        rootView.addView(contentView, params);
        StatusBarCompat.compat(this, rootView, getStatusBar());
        super.setContentView(rootView);
    }

    protected void handlerSaveInstanceState(Bundle savedInstanceState) {
    }

    public Observable ClickView(View view) {
        return RxView.clicks(view).throttleFirst(BuildConfig.TIME_CLICK_IGNORE, TimeUnit.MILLISECONDS).compose(bindUntilEvent(ActivityEvent.DESTROY));
    }

    public void showLoadingDialog() {
        showLoadingDialog(this.getString(R.string.load_loading), false);
    }

    public void showLoadingDialog(String text) {
        showLoadingDialog(text, false);
    }

    public void showLoadingDialog(String loadText, boolean cancelable) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(this, loadText, cancelable);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        progressDialog.setLoadText(loadText);
    }

    public void closeLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    protected View getBaseContentView() {
        return contentView;
    }

    public StatusBarValue getStatusBar() {
        return new StatusBarValue(true, R.color.colorPrimary);
    }

    public void getExtra() {
    }

    /**
     * 利用泛型来findView。非常推荐的一种写法，能简化不少代码
     * @param resId
     * @param <V>
     * @return
     */
    protected <V extends View> V findView(int resId) {
        return (V) contentView.findViewById(resId);
    }

    @Override
    protected void onDestroy() {
        closeLoadingDialog();
        progressDialog = null;
        super.onDestroy();
    }

    protected abstract int getLayoutId();

    protected abstract void init();

    protected abstract void setListener();
}