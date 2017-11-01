package cn.xiaolong.ticketsystem.ui.trendanalysis;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.standards.library.app.AppContext;
import com.standards.library.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.adapter.TicketGroupListAdapter;
import cn.xiaolong.ticketsystem.base.BaseTitleBar;
import cn.xiaolong.ticketsystem.base.BaseTitleBarActivity;
import cn.xiaolong.ticketsystem.bean.TicketRegular;
import cn.xiaolong.ticketsystem.presenter.NumberGeneratePresenter;
import cn.xiaolong.ticketsystem.presenter.view.INumberGenerateView;
import cn.xiaolong.ticketsystem.utils.LaunchUtil;

/**
 * @author xiaolong
 * @version v1.0
 * @function <随机摇号>
 * @date: 2017/9/19 17:29
 */

public class CodeGenerateActivity extends BaseTitleBarActivity<NumberGeneratePresenter> implements INumberGenerateView, SensorEventListener {

    public static final int REQUEST_NUMBER_BASE = 0X111;
    private AppCompatSpinner spGenerateCount;
    private TicketRegular mTicketRegular;
    private List<List<String>> numberBase;
    private TextView tvChooseNum;
    private TicketGroupListAdapter ticketGroupListAdapter;
    private RecyclerView rvNumberResult;
    private SensorManager mSensorManager;
    private Sensor mAccelerometerSensor;
    private boolean isShaking = false;

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
    public void initTitleBar(BaseTitleBar titleBar) {
        titleBar.setTitleText("随机摇号");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_number_generate;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //获取 SensorManager 负责管理传感器
        mSensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE));
        if (mSensorManager != null) {
            //获取加速度传感器
            mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (mAccelerometerSensor != null) {
                mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
            }
        }

    }

    @Override
    protected void init() {
        spGenerateCount = findView(R.id.spGenerateCount);
        tvChooseNum = findView(R.id.tvChooseNum);
        SpinnerAdapter spinnerAdapter = new ArrayAdapter(this, R.layout.item_spinner_array,
                new String[]{"1 注", "2 注", "5 注", "10 注", "20 注", "50 注"});
        spGenerateCount.setAdapter(spinnerAdapter);
        mPresenter = new NumberGeneratePresenter(this, mTicketRegular);
        rvNumberResult = findView(R.id.rvNumberResult);
        ticketGroupListAdapter = new TicketGroupListAdapter(this, new ArrayList<>());
        rvNumberResult.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvNumberResult.setAdapter(ticketGroupListAdapter);
    }

    @Override
    protected void setListener() {
        ClickView(findView(R.id.llNumberBase))
                .subscribe(o -> LaunchUtil.launchActivity(this, CodeBaseSelectActivity.class,
                        CodeBaseSelectActivity.buildBundle(mTicketRegular, numberBase), REQUEST_NUMBER_BASE));
        spGenerateCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                generateGroup();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ClickView(findView(R.id.tvClear)).subscribe(o -> {
            ticketGroupListAdapter.getCodeGroups().clear();
            ticketGroupListAdapter.notifyDataSetChanged();
        });
        ClickView(findView(R.id.tvCopy)).subscribe(o -> {
            ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
            StringBuilder sb = new StringBuilder();
            for (String str : ticketGroupListAdapter.getCodeGroups()) {
                sb.append(str).append("\n");
            }
            clipboardManager.setPrimaryClip(ClipData.newPlainText(null, sb));
            ToastUtil.showToast("已经复制到剪切板");
        });
        ClickView(findView(R.id.tvShake)).subscribe(o -> {
            shake();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NUMBER_BASE && resultCode == CodeBaseSelectActivity.RESULT_NUMBER_BASE) {
            numberBase = (List<List<String>>) data.getSerializableExtra("codeBase");
            int baseCount = Integer.valueOf(mTicketRegular.regular.split("\\+")[0]);
            int baseLength = 0;
            String result = "";
            for (List<String> stringList : numberBase) {
                if (stringList.size() == 0) {
                    result += "X ";
                }
                for (String str : stringList) {
                    result += (str.equals("") ? "X" : str) + " ";
                    baseCount--;
                    if (baseCount == 0) {
                        baseLength = result.length();
                    }
                }
            }
            SpannableString spannableString = new SpannableString(result);
            spannableString.setSpan(new ForegroundColorSpan(AppContext.getContext().getResources().getColor(R.color.main_blue_color_4c65ed)),
                    baseLength, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvChooseNum.setText(spannableString);
            generateGroup();
        }
    }

    @Override
    public void onGenerateDataSuccess(String codeGroup) {
        ticketGroupListAdapter.addCodeGroup(codeGroup);
    }

    public void generateGroup() {
        ticketGroupListAdapter.getCodeGroups().clear();
        String selectNumber = spGenerateCount.getSelectedItem().toString();
        int count = Integer.parseInt(selectNumber.substring(0, selectNumber.length() - 2));
        for (int i = 0; i < count; i++) {
            mPresenter.generaterNumber(numberBase);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 务必要在pause中注销 mSensorManager
        // 否则会造成界面退出后摇一摇依旧生效的bug
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();

        if (type == Sensor.TYPE_ACCELEROMETER) {
            //获取三个方向值
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            if ((Math.abs(x) > 17 || Math.abs(y) > 17 || Math
                    .abs(z) > 17) && !isShaking) {
                shake();
            }
        }

    }

    private void shake() {
        SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        int weiChatAudio = soundPool.load(CodeGenerateActivity.this, R.raw.weichat_audio, 1);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        //发出提示音
        soundPool.play(weiChatAudio, 1, 1, 0, 0, 1);
        vibrator.vibrate(300);
        generateGroup();
        isShaking = true;
        try {
            Thread.sleep(100);
            isShaking = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
