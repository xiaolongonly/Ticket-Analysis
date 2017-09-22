package cn.xiaolong.ticketsystem.api;

import com.standards.library.model.ListData;
import com.standards.library.model.Response;

import java.util.List;

import cn.xiaolong.ticketsystem.bean.TicketInfo;
import cn.xiaolong.ticketsystem.bean.TicketOpenData;
import cn.xiaolong.ticketsystem.bean.TicketType;
import retrofit2.http.Field;
import rx.Observable;

/**
 * <请描述这个类是干什么的>
 *
 * @data: 2016/7/7 13:55
 * @version: V1.0
 */
public class DataManager extends ResponseHandle {

    //获取所有彩票列表
    public static Observable<ListData<TicketType>> getTicketList() {
        return Dao.getApiService().getTicketList(null)
                .flatMap(newEntityData())
                .compose(applySchedulersWithToken());
    }

    //获取最新开奖信息
    public static Observable<ListData<TicketOpenData>> getNewestOpen(String code) {
        return Dao.getApiService().getNewestOpen(code)
                .flatMap(newEntityData())
                .compose(applySchedulersWithToken());
    }

    //获取单期开奖信息
    public static Observable<ListData<TicketOpenData>> getSinglePeroidCheck(String code, String issue) {
        return Dao.getApiService().getSinglePeroidCheck(code, issue)
                .flatMap(newEntityData())
                .compose(applySchedulersWithToken());
    }

    //获取多期开奖信息
    public static Observable<ListData<TicketOpenData>> getMutiPeriodCheck(String code, String count, String endTime) {
        return Dao.getApiService().getMutiPeriodCheck(code, endTime, count)
                .flatMap(newEntityData())
                .compose(applySchedulersWithToken());
    }
}
