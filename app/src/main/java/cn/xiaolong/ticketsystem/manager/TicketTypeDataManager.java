package cn.xiaolong.ticketsystem.manager;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.standards.library.cache.SPHelp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.xiaolong.ticketsystem.BuildConfig;
import cn.xiaolong.ticketsystem.api.DataManager;
import cn.xiaolong.ticketsystem.api.NoCacheException;
import cn.xiaolong.ticketsystem.bean.TicketType;
import rx.Observable;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/11 11:31
 */

public class TicketTypeDataManager {
    private static TicketTypeDataManager sTicketDataManager;
    /**
     * 地方
     */
    private static List<TicketType> mAreaTypeData;
    /**
     * 全国
     */
    private static List<TicketType> mCountryTypeData;

    /**
     * 境外
     */
    private static List<TicketType> mOutTypeData;

//    /**
//     * 关注
//     */
//    private static List<TicketType> mFollowTypeData;

    /**
     * 高频
     */
    private static List<TicketType> mHighRateTypeData;

    /**
     * 全部
     */
    private static List<TicketType> mAllTypeData;

    public static TicketTypeDataManager getTicketDataManager() {
        if (sTicketDataManager == null) {
            synchronized (TicketTypeDataManager.class) {
                if (sTicketDataManager == null) {
                    sTicketDataManager = new TicketTypeDataManager();
                }
            }
        }
        return sTicketDataManager;
    }

    /**
     * 获取地方
     *
     * @return
     */
    public Observable<List<TicketType>> getAreaTypeData() {
        return mAreaTypeData == null ?
                getAllData()
                        .flatMap(ticketTypes -> {
                            List<TicketType> areaTypeList = new ArrayList<TicketType>();
                            for (TicketType ticketType : ticketTypes) {
                                if (!ticketType.area.equals("") && !ticketType.issuer.equals("境外")) {
                                    areaTypeList.add(ticketType);
                                }
                            }
                            Collections.sort(areaTypeList);
                            return Observable.just(areaTypeList);
                        }).doOnNext(ticketTypes -> mAreaTypeData = ticketTypes) : Observable.just(mAreaTypeData);
    }

    /**
     * 获取高频
     *
     * @return
     */
    public Observable<List<TicketType>> getHighTypeData() {
        return mHighRateTypeData == null ?
                getAllData()
                        .flatMap(ticketTypes -> {
                            List<TicketType> highTypeList = new ArrayList<TicketType>();
                            for (TicketType ticketType : ticketTypes) {
                                if (ticketType.high.equals("true")) {
                                    highTypeList.add(ticketType);
                                }
                            }
                            Collections.sort(highTypeList);
                            return Observable.just(highTypeList);
                        }).doOnNext(ticketTypes -> mHighRateTypeData = ticketTypes) : Observable.just(mHighRateTypeData);
    }

    /**
     * 获取境外
     *
     * @return
     */
    public Observable<List<TicketType>> getOutTypeData() {
        return mOutTypeData == null ?
                getAllData()
                        .flatMap(ticketTypes -> {
                            List<TicketType> outTypeList = new ArrayList<TicketType>();
                            for (TicketType ticketType : ticketTypes) {
                                if (ticketType.issuer.equals("境外")) {
                                    outTypeList.add(ticketType);
                                }
                            }
                            Collections.sort(outTypeList);
                            return Observable.just(outTypeList);
                        })
                        .doOnNext(ticketTypes -> mOutTypeData = ticketTypes) : Observable.just(mOutTypeData);
    }

    /**
     * 获取全部
     *
     * @return
     */
    public Observable<List<TicketType>> getAllData() {
        return mAllTypeData == null ?
                DataManager
                        .getTicketList()
                        .flatMap(ticketTypeListData -> {
                            Collections.sort(ticketTypeListData.list);
                            return Observable.just(ticketTypeListData.list);
                        })
                        .doOnNext(allData -> mAllTypeData = allData)
                        .flatMap(allData -> Observable.just(allData)
                        )
                : Observable.just(mAllTypeData);
    }

    /**
     * 获取全国
     *
     * @return
     */
    public Observable<List<TicketType>> getCountryData() {
        return mCountryTypeData == null ?
                getAllData()
                        .flatMap(ticketTypes -> {
                            List<TicketType> countryTypeList = new ArrayList<TicketType>();
                            for (TicketType ticketType : ticketTypes) {
                                if (ticketType.area.equals("") && !ticketType.issuer.equals("境外")) {
                                    countryTypeList.add(ticketType);
                                }
                            }
                            Collections.sort(countryTypeList);
                            return Observable.just(countryTypeList);
                        }).doOnNext(ticketTypes -> mCountryTypeData = ticketTypes) : Observable.just(mCountryTypeData);
    }

    /**
     * 获取关注，关注的列表将直接缓存到本地/毕竟我们木有服务器
     *
     * @return
     */
    public Observable<List<TicketType>> getMyFollowData() {

        return getDataFromCache().flatMap(ticketTypes ->
                ticketTypes == null ? Observable.just(new ArrayList<TicketType>()) : Observable.just(ticketTypes)
        );
    }

    private Observable<List<TicketType>> getDataFromCache() {
        return Observable.create(subscriber -> {
            String str = (String) SPHelp.getUserParam(BuildConfig.KEY_MY_FOLLOW, "");
            List<TicketType> ticketTypes = new Gson().fromJson(str, new TypeToken<List<TicketType>>() {
            }.getType());
            subscriber.onNext(ticketTypes);
            subscriber.onCompleted();
        });
    }

    /**
     * 是否刷新数据
     */
    public TicketTypeDataManager refreshData() {
        mAllTypeData = null;
        mAreaTypeData = null;
        mOutTypeData = null;
        mCountryTypeData = null;
//        mFollowTypeData = null;
        mHighRateTypeData = null;
        return sTicketDataManager;
    }
}
