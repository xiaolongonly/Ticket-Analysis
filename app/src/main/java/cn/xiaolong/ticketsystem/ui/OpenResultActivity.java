package cn.xiaolong.ticketsystem.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;


import com.standards.library.util.TimeUtils;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.adapter.OpenCodeAdapter;
import cn.xiaolong.ticketsystem.base.BaseTitleBar;
import cn.xiaolong.ticketsystem.base.BaseTitleBarActivity;
import cn.xiaolong.ticketsystem.bean.TicketOpenData;
import cn.xiaolong.ticketsystem.bean.TicketRegular;
import cn.xiaolong.ticketsystem.bean.TicketType;
import cn.xiaolong.ticketsystem.presenter.OpenResultPresenter;
import cn.xiaolong.ticketsystem.presenter.view.IOpenResultView;
import cn.xiaolong.ticketsystem.ui.trendanalysis.AverageSimulateActivity;
import cn.xiaolong.ticketsystem.ui.trendanalysis.AvgAnalysisActivity;
import cn.xiaolong.ticketsystem.ui.trendanalysis.CodeForecastActivity;
import cn.xiaolong.ticketsystem.ui.trendanalysis.CodeGenerateActivity;
import cn.xiaolong.ticketsystem.ui.trendanalysis.CodeRateActivity;
import cn.xiaolong.ticketsystem.ui.trendanalysis.ParityTrendActivity;
import cn.xiaolong.ticketsystem.ui.trendanalysis.SumAnalysisActivity;
import cn.xiaolong.ticketsystem.utils.LaunchUtil;

/**
 * @author xiaolong
 * @version v1.0
 * @function <开奖结果>
 * @date: 2017/9/8 14:32
 */

public class OpenResultActivity extends BaseTitleBarActivity<OpenResultPresenter> implements IOpenResultView {
    private TextView tvTitle;
    private TextView tvOpenSerial;
    private TextView tvOpenTime;
    private TextView tvHistory;
    private TicketType mTicketType;
    private TicketOpenData mTicketOpenData;
    private RecyclerView rvOpenResult;

    @Override
    public OpenResultPresenter getPresenter() {
        return new OpenResultPresenter(this);
    }

    public static Bundle buildBundle(TicketType ticketType) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("ticketType", ticketType);
        return bundle;
    }

    public static Bundle buildBundle(TicketOpenData ticketOpenData) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("ticketOpenData", ticketOpenData);
        return bundle;
    }

    @Override
    public void getExtra() {
        super.getExtra();
        mTicketType = (TicketType) getIntent().getSerializableExtra("ticketType");
        mTicketOpenData = (TicketOpenData) getIntent().getSerializableExtra("ticketOpenData");
    }

    @Override
    public void initTitleBar(BaseTitleBar titleBar) {
        tvTitle = (TextView) titleBar.center;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_open_result;
    }

    @Override
    protected void init() {
        rvOpenResult = findView(R.id.rvOpenResult);
        tvOpenSerial = findView(R.id.tvOpenSerial);
        tvOpenTime = findView(R.id.tvOpenTime);
        tvHistory = findView(R.id.tvHistory);
        if (mTicketType != null) {
            tvTitle.setText(mTicketType.descr);
            mPresenter.getSingleOpenResult(mTicketType.code, "");
            mPresenter.getRegularCache(mTicketType.code);
        }
        if (mTicketOpenData != null) {
            tvTitle.setText(mTicketOpenData.name);
            mPresenter.getSingleOpenResult(mTicketType.code, mTicketOpenData.expect);
        }
    }

    @Override
    protected void setListener() {
        tvHistory.setOnClickListener(v ->
        {
            if (mTicketType == null) {
                finish();
            } else {
                LaunchUtil.launchActivity(this, HistoryActivity.class, HistoryActivity.buildBundle(mTicketType));
            }

        });
        /**
         * 奇偶趋势分析
         */
        ClickView(findView(R.id.tvParityTrend))
                .subscribe(o -> LaunchUtil.launchActivity(this, ParityTrendActivity.class,
                        ParityTrendActivity.buildBundle(mTicketType)));

        /**
         * 均值分析
         */
        ClickView(findView(R.id.tvAvgAnalysis))
                .subscribe(o -> LaunchUtil.launchActivity(this, AvgAnalysisActivity.class,
                        AvgAnalysisActivity.buildBundle(mTicketType)));
        /**
         * 和值分析
         */
        ClickView(findView(R.id.tvSumAnalysis))
                .subscribe(o -> LaunchUtil.launchActivity(this, SumAnalysisActivity.class,
                        SumAnalysisActivity.buildBundle(mTicketType)));
        /**
         * 号码频率
         */
        ClickView(findView(R.id.tvNumRate))
                .subscribe(o -> LaunchUtil.launchActivity(this, CodeRateActivity.class,
                        CodeRateActivity.buildBundle(mTicketType)));
        /**
         * 均值演算
         */
        ClickView(findView(R.id.tvAvgSimulate))
                .subscribe(o -> LaunchUtil.launchActivity(this, AverageSimulateActivity.class));
    }

    @Override
    public void getSingleOpenResultSuccess(TicketOpenData ticketOpenData) {
        OpenCodeAdapter openCodeAdapter = new OpenCodeAdapter(this, ticketOpenData.openCode);
        rvOpenResult.setLayoutManager(new GridLayoutManager(this, 7));
        rvOpenResult.setAdapter(openCodeAdapter);
        tvOpenTime.setText("开奖日期：" + TimeUtils.milliseconds2String(ticketOpenData.timestamp * 1000));
        tvOpenSerial.setText("第" + ticketOpenData.expect + "期");
    }

    @Override
    public void getRegularSuccess(TicketRegular ticketRegular) {
        ClickView(findView(R.id.tvRandomNum))
                .subscribe(o -> LaunchUtil.launchActivity(this, CodeGenerateActivity.class,
                        CodeGenerateActivity.buildBundle(ticketRegular)));
        /**
         * 号码预测
         */
        ClickView(findView(R.id.tvNumberForecast))
                .subscribe(o -> LaunchUtil.launchActivity(this, CodeForecastActivity.class, CodeForecastActivity.buildBundle(ticketRegular)));
    }
}
