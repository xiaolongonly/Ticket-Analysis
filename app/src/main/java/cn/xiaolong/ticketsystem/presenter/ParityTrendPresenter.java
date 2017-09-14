package cn.xiaolong.ticketsystem.presenter;

import android.app.Activity;

import com.standards.library.util.TimeUtils;

import cn.xiaolong.ticketsystem.api.DataManager;
import cn.xiaolong.ticketsystem.base.BasePresenter;
import cn.xiaolong.ticketsystem.presenter.view.IParityTrendView;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/14 14:58
 */

public class ParityTrendPresenter extends BasePresenter<IParityTrendView> {
    public ParityTrendPresenter(Activity activity) {
        super(activity);
    }

    public void getRecentOpenDatas(String ticketCode, String count) {
        addSubscribe(DataManager.getMutiPeriodCheck(ticketCode, count, TimeUtils.milliseconds2String(System.currentTimeMillis()))
                .subscribe(getSubscriber(ticketOpenDataListData -> {
                    mView.onGetHistoryRecentTicketListSuccess(ticketOpenDataListData.list);
                })));
    }
}
