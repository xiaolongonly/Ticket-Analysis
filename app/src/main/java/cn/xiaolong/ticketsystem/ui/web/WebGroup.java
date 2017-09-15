package cn.xiaolong.ticketsystem.ui.web;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.standards.library.app.AppContext;
import com.standards.library.app.ReturnCode;
import com.standards.library.listview.loading.IGroupLoadingHelp;

import cn.xiaolong.ticketsystem.R;


/**
 *
 * @version: V1.0
 */
public class WebGroup {
    private View contentView;
    private BaseWebView webView;
    private IGroupLoadingHelp loadingHelp;
    private WebConfig config;

    public static WebGroup create(Context context, WebConfig config, IGroupLoadingHelp loadingHelp) {
        WebGroup group = new WebGroup();
        group.contentView = LayoutInflater.from(context).inflate(R.layout.web_group, null);
        group.webView = (BaseWebView) group.contentView.findViewById(R.id.webview);
        group.loadingHelp = loadingHelp;
        group.config = config;
        group.initWebGroup();
        group.request(group.config.url);
        return group;
    }

    protected void initWebGroup() {
        loadingHelp.createLoadingPage((RelativeLayout) contentView);
        loadingHelp.setOnFailClickListener(failCode -> request(config.url));

        if (!loadingHelp.isShowLaunchPage()) {
            loadingHelp.showLoading();
        }
        webView.setHostUrl(config.hostUrl);
        webView.setOnProgressChangedListener(progress -> {
            if (progress == 100) {
                if (loadingHelp == null) return;
                loadingHelp.hideLoading();
            }
            if (onProgressChangedListener == null) return;
            onProgressChangedListener.progress(progress);
        });
        webView.defaultSetting(config);
    }

    private void request(String url) {
        if (!AppContext.isNetworkAvailable()) {
            loadingHelp.showFailPage(ReturnCode.LOCAL_NO_NETWORK);
            return;
        }
        if (url == null || url.equals("")) return;
        webView.loadUrl(url);
    }

    public void reloadContent(String url) {
        if (!loadingHelp.isShowLaunchPage()) {
            loadingHelp.showLoading();
        }
        request(url);
    }


    public void reloadContent(String url, WebConfig webConfig) {
        webView.setHostUrl(config.hostUrl);
        webView.defaultSetting(webConfig);
        reloadContent(url);
    }

    public BaseWebView getWebView() {
        return webView;
    }

    public View getRootView() {
        return contentView;
    }

    private BaseWebView.OnProgressChangedListener onProgressChangedListener;

    public void setOnProgressChangedListener(BaseWebView.OnProgressChangedListener onProgressChangedListener) {
        this.onProgressChangedListener = onProgressChangedListener;
    }

    public void setOnShouldOverrideUrlListener(BaseWebView.OnShouldOverrideUrlListener onShouldOverrideUrlListener) {
        webView.setShouldOverrideUrlListener(onShouldOverrideUrlListener);
    }

    public void setOnPageFinishListener(BaseWebView.OnPageFinishListener onPageFinishListener) {
        webView.setOnPageFinishListener(onPageFinishListener);
    }

    public void setOnReceiveTitleListener(BaseWebView.OnReceiveTitleListener onReceiveTitleListener) {
        webView.setOnReceiveTitleListener(onReceiveTitleListener);
    }
}
