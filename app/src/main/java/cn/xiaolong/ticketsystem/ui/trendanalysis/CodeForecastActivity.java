package cn.xiaolong.ticketsystem.ui.trendanalysis;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.adapter.CodeGroupAdapter;
import cn.xiaolong.ticketsystem.base.BaseTitleBar;
import cn.xiaolong.ticketsystem.base.BaseTitleBarActivity;
import cn.xiaolong.ticketsystem.bean.TicketOpenData;
import cn.xiaolong.ticketsystem.bean.TicketRegular;
import cn.xiaolong.ticketsystem.presenter.ParityTrendPresenter;
import cn.xiaolong.ticketsystem.presenter.view.IParityTrendView;
import cn.xiaolong.ticketsystem.thread.DefaultThreadFactory;
import cn.xiaolong.ticketsystem.thread.NumberAvgRunnable;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/25 15:24
 */

public class CodeForecastActivity extends BaseTitleBarActivity<ParityTrendPresenter> implements IParityTrendView {
    private TicketRegular mTicketRegular;
    private int taskCount = 0;
    private List<List<Double>> mCodeAvgList;
    private RecyclerView rvForecast;

    @Override
    public void initTitleBar(BaseTitleBar titleBar) {
        titleBar.setTitleText("号码预测");
    }

    public static Bundle buildBundle(TicketRegular ticketRegular) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("ticketRegular", ticketRegular);
        return bundle;
    }

    @Override
    public void getExtra() {
        super.getExtra();
        mTicketRegular = (TicketRegular) getIntent().getSerializableExtra("ticketRegular");
    }

    @Override
    public ParityTrendPresenter getPresenter() {
        return new ParityTrendPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_code_forecast;
    }

    @Override
    protected void init() {
        int cores = Math.max(1, Runtime.getRuntime().availableProcessors());
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(cores, cores, 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>(), new DefaultThreadFactory());
        Handler handler = getHandler(cores);
        rvForecast = (RecyclerView) findViewById(R.id.rvForecast);
        showLoadingDialog("正在拼命计算中...");
        for (int i = 0; i < cores; i++) {
            threadPoolExecutor.execute(new NumberAvgRunnable(handler, mTicketRegular.regular,
                    mTicketRegular.codeDis, mTicketRegular.repeat));
            taskCount++;
        }
    }

    @Override
    protected void setListener() {

    }

    /**
     * handler 消息处理，这边将每个线程最终计算的均值再取平均。得到真实的平均
     *
     * @param cores
     * @return
     */
    private Handler getHandler(final int cores) {

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                if (mCodeAvgList == null) {
                    mCodeAvgList = (List<List<Double>>) bundle.getSerializable("codeAvgList");
                } else {
                    List<List<Double>> codeAcgList = (List<List<Double>>) bundle.getSerializable("codeAvgList");
                    for (int i = 0; i < codeAcgList.size(); i++) {
                        for (int j = 0; j < codeAcgList.get(i).size(); j++) {
                            mCodeAvgList.get(i).set(j, (mCodeAvgList.get(i).get(j) + codeAcgList.get(i).get(j)) / 2);
                        }
                    }
                }
                taskCount--;
                if (taskCount == 0) {
                    mPresenter.getRecentOpenDatas(mTicketRegular.code, "10");
                }

            }
        };
        return handler;
    }

    @Override
    public void onGetHistoryRecentTicketListSuccess(List<TicketOpenData> list) {
        List<List<Double>> dataAvg = new ArrayList<>();
        for (TicketOpenData ticketOpenData : list) {
            String[] openResult = ticketOpenData.openCode.split("\\+");
            List<List<Double>> data = new ArrayList<>();
            for (int i = 0; i < openResult.length; i++) {
                String[] codes = openResult[i].split(",");
                if (data.size() <= i) {
                    data.add(new ArrayList<>());
                }
                for (int k = 0; k < codes.length; k++) {
                    data.get(i).add(Double.valueOf(codes[k]));
                }
                if (!mTicketRegular.repeat) {
                    Collections.sort(data.get(i));
                }
            }
            if (dataAvg.size() == 0) {
                dataAvg = data;
            } else {
                for (int i = 0; i < data.size(); i++) {
                    for (int j = 0; j < data.get(i).size(); j++) {
                        dataAvg.get(i).set(j, (dataAvg.get(i).get(j) + data.get(i).get(j)) / 2);
                    }
                }
            }
        }
        String result = "";
        for (int i = 0; i < dataAvg.size(); i++) {
            for (int j = 0; j < dataAvg.get(i).size(); j++) {
                result += Math.round(dataAvg.get(i).get(j) + mCodeAvgList.get(i).get(j)) / 2 + ",";
            }
            result.substring(0,result.length()-1);
            result+="+";
        }
        result.substring(0,result.length()-1);
        CodeGroupAdapter codeGroupAdapter = new CodeGroupAdapter(this, result);
        rvForecast.setLayoutManager(new GridLayoutManager(this, 7));
        rvForecast.setAdapter(codeGroupAdapter);
    }
}
