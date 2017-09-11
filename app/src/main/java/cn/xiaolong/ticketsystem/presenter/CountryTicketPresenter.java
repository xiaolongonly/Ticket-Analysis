package cn.xiaolong.ticketsystem.presenter;

import android.app.Activity;

import cn.xiaolong.ticketsystem.api.DataManager;
import cn.xiaolong.ticketsystem.base.BasePresenter;
import cn.xiaolong.ticketsystem.presenter.view.IOpenResultView;
import cn.xiaolong.ticketsystem.presenter.view.ITicketListView;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/09/11 11:10:50
 */

public class CountryTicketPresenter extends BasePresenter<ITicketListView> {

    public CountryTicketPresenter(ITicketListView view, Activity activity) {
        super(view, activity);
    }

    public void getTicketList() {
        addSubscribe(DataManager.getTicketList().subscribe(getSubscriber(ticketTypeListData ->
                mView.onGetTicketListSuccess(ticketTypeListData)
        )));
    }
}
