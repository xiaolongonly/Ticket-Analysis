package cn.xiaolong.ticketsystem.manager;

import android.content.Context;
import android.widget.Switch;

import com.standards.library.listview.manager.BaseGroupListManager;
import com.standards.library.model.ListData;

import java.util.ArrayList;
import java.util.List;

import cn.xiaolong.ticketsystem.api.DataManager;
import cn.xiaolong.ticketsystem.bean.TicketOpenData;
import cn.xiaolong.ticketsystem.bean.TicketType;
import cn.xiaolong.ticketsystem.bean.type.TicketTypeEnum;
import rx.Observable;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date 2017年7月13日 15:24:51
 */
public class TicketTypeManager extends BaseGroupListManager<TicketType> {
    private TicketTypeEnum mTicketTypeEnum;

    public TicketTypeManager(TicketTypeEnum ticketTypeEnum) {
        mTicketTypeEnum = ticketTypeEnum;
    }

    @Override
    protected Observable<ListData<TicketType>> getData(Context context) {
        return getTicketListByType(mTicketTypeEnum);
    }

    private Observable<ListData<TicketType>> getTicketListByType(TicketTypeEnum mTicketTypeEnum) {
//        if (mTicketTypeEnum == TicketTypeEnum.All) {
//            return TicketTypeDataManager
//                    .getTicketDataManager()
//                    .getAllData()
//                    .flatMap(ticketTypes -> translateToListData(ticketTypes));
//        }
        if (mTicketTypeEnum == TicketTypeEnum.Country) {
            return TicketTypeDataManager
                    .getTicketDataManager()
                    .getCountryData()
                    .flatMap(ticketTypes -> translateToListData(ticketTypes));
        }
        if (mTicketTypeEnum == TicketTypeEnum.Area) {
            return TicketTypeDataManager
                    .getTicketDataManager()
                    .getAreaTypeData()
                    .flatMap(ticketTypes -> translateToListData(ticketTypes));
        }

        if (mTicketTypeEnum == TicketTypeEnum.Out) {
            return TicketTypeDataManager
                    .getTicketDataManager()
                    .getOutTypeData()
                    .flatMap(ticketTypes -> translateToListData(ticketTypes));
        }

        if (mTicketTypeEnum == TicketTypeEnum.High) {
            return TicketTypeDataManager
                    .getTicketDataManager()
                    .getHighTypeData()
                    .flatMap(ticketTypes -> translateToListData(ticketTypes));
        }

        if (mTicketTypeEnum == TicketTypeEnum.Follow) {
            return TicketTypeDataManager
                    .getTicketDataManager()
                    .getMyFollowData()
                    .flatMap(ticketTypes -> translateToListData(ticketTypes));
        }
        return TicketTypeDataManager
                .getTicketDataManager()
                .getAllData()
                .flatMap(ticketTypes -> translateToListData(ticketTypes));
    }


    private Observable<ListData<TicketType>> translateToListData(List<TicketType> ticketTypes) {
        ListData<TicketType> listData = new ListData();
        listData.list = ticketTypes;
        return Observable.just(listData);
    }


}
