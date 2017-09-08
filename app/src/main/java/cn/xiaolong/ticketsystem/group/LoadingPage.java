package cn.xiaolong.ticketsystem.group;

import android.content.Context;
import android.view.ViewGroup;

import com.standards.library.app.ReturnCode;
import com.standards.library.util.LogUtil;


/**
 * <加载页>
 *
 * @data: 2015/11/23 9:39
 * @version: V1.0
 */
public class LoadingPage extends CountyLoadingPage {

    private Scene scene;
    private LoadEntity loadEntity;

    public LoadingPage(Context context, Scene scene) {
        super(context);
        this.scene = scene;
        loadEntity = LoadingConfig.createEntity(scene);
    }

    public LoadingPage(ViewGroup viewGroup, Scene scene) {
        super(viewGroup.getContext());
        this.scene = scene;
        loadEntity = LoadingConfig.createEntity(scene);
        createLoadingPage(viewGroup);
    }

    public void changeScene(Scene scene) {
        if (this.scene == scene) {
            return;
        }
        loadEntity = LoadingConfig.createEntity(scene);
    }


    @Override
    public void showFailPage(int failCode) {
        super.showFailPage(failCode);
        LogUtil.d(LoadingPage.class.getSimpleName(), failCode + "");
        hideLoading();

        getLoadViewHelp().initView(mContext);
        addView(getLoadViewHelp().getRootView());
        LoadResource loadResource = loadEntity.getMessage(failCode);
        if (loadResource == null) {
            return;
        }

        getLoadViewHelp().showErrorPage(failCode, loadResource);
    }

    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void showEmptyLoadingPage() {
        hideLoading();

        getLoadViewHelp().initView(mContext);
        addView(getLoadViewHelp().getRootView());
        getLoadViewHelp().showErrorPage(ReturnCode.CODE_EMPTY, loadEntity.getMessage(ReturnCode.CODE_EMPTY));
    }

}

