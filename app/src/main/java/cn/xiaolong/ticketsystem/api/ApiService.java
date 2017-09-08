package cn.xiaolong.ticketsystem.api;


import com.standards.library.model.ListData;
import com.standards.library.model.Response;

import cn.xiaolong.ticketsystem.bean.TicketList;
import cn.xiaolong.ticketsystem.bean.TicketOpenData;
import cn.xiaolong.ticketsystem.bean.TicketType;
import cn.xiaolong.ticketsystem.bean.TicketInfo;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * <请描述这个类是干什么的>
 *
 * @author caiyk@cncn.com
 * @data: 2016/7/7 11:28
 * @version: V1.0
 */
public interface ApiService {

    /**
     * 查询支持彩票类别
     *
     * @param extra
     * @return
     */
    @FormUrlEncoded
    @POST("44-6")
    Observable<Response<ListData<TicketType>>> getTicketList(@Field("extra") String extra);

    /**
     * 最新开奖参数
     *
     * @param code （非必须）彩票类型  彩票类型，查询多个以“|”分隔，无则返回全部彩票最新开奖信息
     * @return
     */
    @FormUrlEncoded
    @POST("44-1")
    Observable<Response<ListData<TicketOpenData>>> getNewestOpen(@Field("code") String code);

    /**
     * 多期开奖查询
     *
     * @param code    彩票类型
     * @param endTime （非必须） 截止时间，格式为“2015-02-26 21:35:00”的形式。该参数为过滤条件，
     *                表示只查询该时间前的开奖信息，无则使用服务器当前时间
     * @param count   （非必须） 取多少条开奖数据,最多支持每次50条
     * @return
     */
    @FormUrlEncoded
    @POST("44-2")
    Observable<Response<ListData<TicketOpenData>>> getMutiPeriodCheck(@Field("code") String code,
                                                                      @Field("endTime") String endTime,
                                                                      @Field("count") String count);

    /**
     * 单期开奖查询
     *
     * @param code   彩票类型
     * @param expect (非必须)第几期彩票。无则表示查询最新一期
     * @return
     */
    @FormUrlEncoded
    @POST("44-2")
    Observable<Response<ListData<TicketOpenData>>> getSinglePeroidCheck(@Field("code") String code,
                                                                        @Field("expect") String expect);
}

