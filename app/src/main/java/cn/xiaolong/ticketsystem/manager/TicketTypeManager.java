package cn.xiaolong.ticketsystem.manager;

import android.content.Context;

import com.standards.library.listview.manager.BaseGroupListManager;
import com.standards.library.model.ListData;

import cn.xiaolong.ticketsystem.api.DataManager;
import cn.xiaolong.ticketsystem.bean.TicketType;
import rx.Observable;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date 2017年7月13日 15:24:51
 */
public class TicketTypeManager extends BaseGroupListManager<TicketType> {

    public TicketTypeManager() {
    }

    @Override
    protected Observable<ListData<TicketType>> getData(Context context) {
        return DataManager.getTicketList();
    }

}
