package cn.xiaolong.ticketsystem.api;

import com.standards.library.network.retrofit.RetrofitDao;

import cn.xiaolong.ticketsystem.BuildConfig;
import okhttp3.HttpUrl;


/**
 * <请描述这个类是干什么的>
 *
 * @data: 2016/7/7 11:28
 * @version: V1.0
 */
public class Dao {
    private static ApiService mApiService;

    public static ApiService getApiService() {
        if (mApiService == null) {
            synchronized (Dao.class) {
                if (mApiService == null) {
                    mApiService = RetrofitDao.buildRetrofit(builder -> buildPublicParams(builder)).create(ApiService.class);
                }
            }
        }
        return mApiService;
    }

    private static HttpUrl.Builder buildPublicParams(HttpUrl.Builder builder) {
        builder.addQueryParameter("showapi_sign", BuildConfig.TICKET_SECRET);
        builder.addQueryParameter("showapi_appid", BuildConfig.TICKET_APP_ID);
        return builder;
    }


}
