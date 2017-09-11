package cn.xiaolong.ticketsystem.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.standards.library.app.AppContext;
import com.standards.library.listview.loading.OnFailClickListener;

import cn.xiaolong.ticketsystem.R;

public class LoadingHelp {
    private View loadingView;
    private RelativeLayout rlLoading;
    private ProgressWheel mProgressWheel;
    private TextView tvMsg;

    private RelativeLayout rlFailed;
    private ImageView ivLoadImg;
    private TextView tvLoadMsg;
    //    private TextView btnLoadRefresh;
    private TextView tvLoadHint;
    private OnFailClickListener onFailClickListener;


    public LoadingHelp(Context context) {

    }

    public void initView(Context context) {
        loadingView = LayoutInflater.from(context).inflate(R.layout.tcc_loading_page, null);

        rlLoading = (RelativeLayout) loadingView.findViewById(R.id.rlLoading);
        mProgressWheel = (ProgressWheel) loadingView.findViewById(R.id.progress);
        tvMsg = (TextView) loadingView.findViewById(R.id.tv_msg);


        rlFailed = (RelativeLayout) loadingView.findViewById(R.id.rlFailed);
        ivLoadImg = (ImageView) loadingView.findViewById(R.id.ivLoadImg);
        tvLoadMsg = (TextView) loadingView.findViewById(R.id.tvLoadMsg);
        tvLoadHint = (TextView) loadingView.findViewById(R.id.tvLoadHint);
//        btnLoadRefresh = (TextView) loadingView.findViewById(R.id.btnLoadRefresh);

        rlLoading.setVisibility(View.GONE);
        rlFailed.setVisibility(View.GONE);
    }

    public void showLoading() {
        rlFailed.setVisibility(View.GONE);
        rlLoading.setVisibility(View.VISIBLE);
        tvMsg.setText(AppContext.getString(R.string.load_loading));
    }

    public void showErrorPage(int failCode, LoadResource loadResource) {
        rlLoading.setVisibility(View.GONE);
        rlFailed.setVisibility(View.VISIBLE);
        ivLoadImg.setImageResource(loadResource.getImage());
        tvLoadMsg.setText(loadResource.getErrorText());
        tvLoadHint.setText(loadResource.getErrorHint());
        tvLoadHint.setOnClickListener(v -> {
            if (onFailClickListener != null) {
                onFailClickListener.onFailClick(failCode);
            }
        });
    }

    public View getRootView() {
        return loadingView;
    }

    public void setOnFailClickListener(OnFailClickListener onFailClickListener) {
        this.onFailClickListener = onFailClickListener;
    }
}
