package cn.xiaolong.ticketsystem.presenter.view;

import com.standards.library.model.ListData;

import cn.xiaolong.ticketsystem.base.ILoadingView;
import cn.xiaolong.ticketsystem.bean.TicketType;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/11 11:17
 */

public interface ITicketListView extends ILoadingView {
    void onGetTicketListSuccess(ListData<TicketType> ticketTypeListData);
}
