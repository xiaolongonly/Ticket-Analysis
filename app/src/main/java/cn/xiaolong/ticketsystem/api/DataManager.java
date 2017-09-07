package cn.xiaolong.ticketsystem.api;

import com.standards.library.model.ListData;
import com.standards.library.model.Response;

import java.util.List;

import retrofit2.http.Field;
import rx.Observable;

/**
 * <请描述这个类是干什么的>
 *
 * @data: 2016/7/7 13:55
 * @version: V1.0
 */
public class DataManager extends ResponseHandle {

    //获取appId
    public static Observable<ListData<String>> getTicketList(String appid, String mysecret) {
        return Dao.getApiService().getTicketList(appid, mysecret)
                .flatMap(newEntityData())
                .compose(applySchedulersWithToken());
    }
}
