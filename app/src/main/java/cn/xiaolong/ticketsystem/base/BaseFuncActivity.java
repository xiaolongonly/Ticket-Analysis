package cn.xiaolong.ticketsystem.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.standards.library.model.Event;
import com.standards.library.rx.ErrorThrowable;
import com.standards.library.util.ToastUtil;
import com.standards.library.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


/**
 * <这一层是对BaseActivity的进一步抽象，
 * 1. 实现了Presenter的部分方法showloading,showError等，
 * 2. EventBus注册解除注册>
 *
 * @data: 2016/7/7 11:11
 * @version: V1.0
 */
public abstract class BaseFuncActivity<T extends BasePresenter> extends BaseActivity implements ILoadingView {
    protected T mPresenter;
    private List<EditText> editTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = getPresenter();
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        getBaseContentView().setOnClickListener(v -> hideAllKeyBoard());
        getEditTextView((ViewGroup) getBaseContentView());
    }

    private void getEditTextView(ViewGroup viewGroup) {
        if (editTexts == null) editTexts = new ArrayList<>();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof EditText) {
                editTexts.add((EditText) view);
            }
            if (view instanceof ViewGroup) {
                getEditTextView((ViewGroup) view);
            }
        }
    }

    public T getPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void showError(ErrorThrowable errorThrowable) {
        errorThrowable.printStackTrace();
        closeLoadingDialog();
        ToastUtil.showToast(errorThrowable.msg);
    }

    @Subscribe
    public void logout(Event event) {

    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        closeLoadingDialog();
    }

    public void hideAllKeyBoard() {
        if (editTexts == null) return;
        for (EditText editText : editTexts) {
            Utils.hideInputKeyboard(editText);
        }
    }


}
