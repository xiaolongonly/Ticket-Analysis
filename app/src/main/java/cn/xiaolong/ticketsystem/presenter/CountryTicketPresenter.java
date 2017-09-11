package cn.xiaolong.ticketsystem.presenter;

import android.app.Activity;

import cn.xiaolong.ticketsystem.api.DataManager;
import cn.xiaolong.ticketsystem.base.BasePresenter;
import cn.xiaolong.ticketsystem.presenter.view.IOpenResultView;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/09/11 11:10:50
 */

public class CountryTicketPresenter extends BasePresenter<IOpenResultView> {
    public CountryTicketPresenter(Activity activity) {
        super(activity);
    }

    public void getSingleOpenResult(String code, String issue) {
        addSubscribe(DataManager.getTicketList().subscribe(getSubscriber(ticketOpenDataTicketInfo ->
                mView.getSingleOpenResultSuccess(ticketOpenDataTicketInfo.list.get(0))
        )));
    }
}
