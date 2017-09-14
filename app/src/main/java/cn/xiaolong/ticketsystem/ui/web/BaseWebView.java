package cn.xiaolong.ticketsystem.ui.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * <请描述这个类是干什么的>
 *
 * @data: 2016/6/22 15:21
 * @version: V1.0
 */
public class BaseWebView extends WebView {

    private OnPageFinishListener mOnPageFinishListener;
    private OnShouldOverrideUrlListener mOnShouldOverrideUrlListener;
    private OnReceiveTitleListener mOnReceiveTitleListener;
    private OnProgressChangedListener mOnProgressChangedListener;

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseWebView(Context context) {
        super(context);
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void defaultSetting(final WebConfig config) {
        setVerticalScrollBarEnabled(true);
        setHorizontalScrollBarEnabled(true);
        WebSettings settings = getSettings();
        settings.setTextZoom(100);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setDomStorageEnabled(true);
        //支持手动调整webview大小
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(config.loadWithOver);
        settings.setLayoutAlgorithm(config.isLayoutAlgorithm ? WebSettings.LayoutAlgorithm.SINGLE_COLUMN : WebSettings.LayoutAlgorithm.NORMAL);
        //初始化WebViewClient
        initWebViewClient(config.isJump);

        //初始化WebViewChromeClient
        initWebChromeClient();

        //webView的按键事件
        setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return onBackPress(keyCode, event);
            }

            public boolean onBackPress(int keyCode, KeyEvent event) {
                if (!config.isInterceptKeyEvent) {
                    return false;
                }
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_BACK)) {
                    if (canGoBack()) {
                        goBack();
                        return true;
                    } else {
                        return false;
                    }
                }

                return false;
            }
        });
    }

    public void initWebViewClient(final boolean mIsJump) {
        setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mOnPageFinishListener != null) {
                    mOnPageFinishListener.onPageFinish();
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.substring(0, 5).contains("http"))
                    return false;
                if (!mIsJump) {
                    ((BaseWebView) view).loadCommonUrl(url);
                    return false;
                } else {
                    if (mOnShouldOverrideUrlListener != null) {
                        mOnShouldOverrideUrlListener.load(url);
                    }
                    return true;
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }
        });
    }

    public void initWebChromeClient() {
        setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (mOnProgressChangedListener != null) {
                    mOnProgressChangedListener.progress(newProgress);
                }
            }


            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (mOnReceiveTitleListener != null) {
                    mOnReceiveTitleListener.onReceiveTitle(title);
                }
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
    }

    public void setHostUrl(String hostUrl) {
        if (TextUtils.isEmpty(hostUrl)) return;
    }

    public void loadCommonUrl(String url) {
        super.loadUrl(url);
    }


    public void setOnPageFinishListener(OnPageFinishListener onPageFinishListener) {
        this.mOnPageFinishListener = onPageFinishListener;
    }

    public void setShouldOverrideUrlListener(OnShouldOverrideUrlListener shouldOverrideUrlListener) {
        this.mOnShouldOverrideUrlListener = shouldOverrideUrlListener;
    }

    public void setOnReceiveTitleListener(OnReceiveTitleListener onReceiveTitleListener) {
        this.mOnReceiveTitleListener = onReceiveTitleListener;
    }

    public void setOnProgressChangedListener(OnProgressChangedListener onProgressChangedListener) {
        this.mOnProgressChangedListener = onProgressChangedListener;
    }

    public interface OnReceiveTitleListener {
        void onReceiveTitle(String title);
    }

    public interface OnPageFinishListener {
        void onPageFinish();
    }

    public interface OnShouldOverrideUrlListener {
        void load(String url);
    }

    public interface OnProgressChangedListener {
        void progress(int progress);
    }
}
