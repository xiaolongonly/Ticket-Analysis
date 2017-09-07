package cn.xiaolong.ticketsystem.base;


import com.standards.library.rx.ErrorThrowable;

/**
 * <请描述这个类是干什么的>
 *
 * @version: V1.0
 */
public interface ILoadingView {
    void showLoading();

    void showError(ErrorThrowable errorThrowable);

    void hideLoading();
}
