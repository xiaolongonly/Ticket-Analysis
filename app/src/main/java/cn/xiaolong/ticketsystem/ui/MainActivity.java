package cn.xiaolong.ticketsystem.ui;

import android.util.Log;

import com.standards.library.rx.CSubscriber;
import com.standards.library.rx.ErrorThrowable;
import com.standards.library.util.LogUtil;

import java.util.List;

import cn.xiaolong.ticketsystem.BuildConfig;
import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.api.Dao;
import cn.xiaolong.ticketsystem.api.DataManager;
import cn.xiaolong.ticketsystem.base.BaseTitleBar;
import cn.xiaolong.ticketsystem.base.BaseTitleBarActivity;
import cn.xiaolong.ticketsystem.bean.ListData;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/7 14:45
 */

public class MainActivity extends BaseTitleBarActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        DataManager.getTicketList(BuildConfig.TICKET_APP_ID, BuildConfig.TICKET_SECRET)
                .subscribe(new CSubscriber<com.standards.library.model.ListData<String>>() {
                    @Override
                    public void onPrepare() {

                    }

                    @Override
                    public void onError(ErrorThrowable throwable) {

                    }

                    @Override
                    public void onSuccess(com.standards.library.model.ListData<String> stringListData) {

                    }
                });
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void initTitleBar(BaseTitleBar titleBar) {

    }
}
