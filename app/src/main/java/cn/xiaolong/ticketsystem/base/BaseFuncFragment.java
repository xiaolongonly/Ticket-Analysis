package cn.xiaolong.ticketsystem.base;

import android.os.Bundle;

import com.standards.library.rx.ErrorThrowable;
import com.standards.library.util.ToastUtil;


/**
 * <请描述这个类是干什么的>
 *
 * @author caiyk@cncn.com
 * @data: 2016/7/5 13:44
 * @version: V1.0
 */
public abstract class BaseFuncFragment<T extends BasePresenter> extends BaseFragment implements ILoadingView {
    protected T mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mPresenter = getPresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void showError(ErrorThrowable errorThrowable) {
        closeLoadingDialog();
        ToastUtil.showToast(errorThrowable.msg);
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        closeLoadingDialog();
    }

    protected T getPresenter() {
        return null;
    }

}
