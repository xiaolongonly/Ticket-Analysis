package cn.xiaolong.ticketsystem.ui.trendanalysis;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.standards.library.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.adapter.CodeListAdapter;
import cn.xiaolong.ticketsystem.base.BaseTitleBar;
import cn.xiaolong.ticketsystem.base.BaseTitleBarActivity;
import cn.xiaolong.ticketsystem.thread.DefaultThreadFactory;
import cn.xiaolong.ticketsystem.thread.TicketRandomRunnable;

public class AverageSimulateActivity extends BaseTitleBarActivity {
    private TextView tvGenerate;
    private EditText etAllNum;
    private EditText etChooseSize;
    private EditText etGenerateNum;
    private EditText etNumberEdit;
    private CheckBox cbRepeat;
    private TextView tvAvg;
    private RecyclerView rvInitData;
    private List<Integer> numberList = new ArrayList<>();
    private CodeListAdapter codeListAdapter;
    private double avg;
    private int taskCount = 0;
    private ImageView ivRight;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_average_simulate;
    }

    @Override
    protected void init() {
        avg = 0;
        etAllNum = (EditText) findViewById(R.id.etAllNum);
        etChooseSize = (EditText) findViewById(R.id.etChooseSize);
        etGenerateNum = (EditText) findViewById(R.id.etGenerateNum);
        tvGenerate = (TextView) findViewById(R.id.tvGenerate);
        tvAvg = (TextView) findViewById(R.id.tvAvg);
        cbRepeat = findView(R.id.cbRepeat);
        etNumberEdit = (EditText) findViewById(R.id.etNumberEdit);
        rvInitData = (RecyclerView) findViewById(R.id.rvInitData);
        codeListAdapter = new CodeListAdapter(this, numberList);
        rvInitData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvInitData.setAdapter(codeListAdapter);

    }

    @Override
    protected void setListener() {
        etNumberEdit.setOnEditorActionListener((v, actionId, event) -> {
            if (TextUtils.isEmpty(etNumberEdit.getText())) {
                return true;
            }
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (numberList.contains(Integer.valueOf(etNumberEdit.getText().toString()))) {
                    Toast.makeText(AverageSimulateActivity.this, "已经添加过该数据", Toast.LENGTH_LONG).show();
                    return true;
                }
                numberList.add(Integer.valueOf(etNumberEdit.getText().toString()));
                codeListAdapter.notifyDataSetChanged();
                etNumberEdit.setText("");
                return true;
            }
            return false;
        });
        tvGenerate.setOnClickListener(v -> {
            tvGenerate.setEnabled(false);
            calculateData(numberList, TextUtils.isEmpty(etAllNum.getText().toString()) ? "0" : etAllNum.getText().toString(),
                    TextUtils.isEmpty(etChooseSize.getText().toString()) ? "0" : etChooseSize.getText().toString(),
                    TextUtils.isEmpty(etGenerateNum.getText().toString()) ? "0" : etGenerateNum.getText().toString(), cbRepeat.isChecked());
        });
    }

    private void calculateData(List<Integer> numberList, String allNum, String chooseSize, String generateSize, boolean selected) {
        final int cores = Math.max(1, Runtime.getRuntime().availableProcessors());
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(cores, cores, 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>(), new DefaultThreadFactory());
        Handler handler = getHandler(cores);
        int generateTaskSize = Integer.valueOf(generateSize);
        List<Integer> tasks = taskDivider(generateTaskSize, cores);
        //run all thread
        showLoadingDialog("正在拼命计算中", false);
        for (int i = 0; i < cores; i++) {
            runTask(threadPoolExecutor, handler, allNum, chooseSize, tasks.get(i), numberList,selected);
        }
    }

    /**
     * 将任务平均分配给每个线程
     *
     * @param generateTaskSize
     * @param cores
     * @return
     */
    private List<Integer> taskDivider(int generateTaskSize, int cores) {
        List<Integer> tasks = new ArrayList<>();
        int avgNum = generateTaskSize / cores;
        for (int i = 0; i < cores - 1; i++) {
            tasks.add(avgNum);
        }
        int lastTaskNum = generateTaskSize - ((generateTaskSize / cores) * cores);
        tasks.add(avgNum + lastTaskNum);
        return tasks;
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
                avg += bundle.getDouble("avg");
                taskCount--;
                if (taskCount == 0) {
                    tvAvg.setText("平均值：" + (avg / cores));
                    avg = 0;
                    taskCount = 0;
                    tvGenerate.setEnabled(true);
                    hideLoading();
                }

            }
        };
        return handler;
    }

    private void runTask(ThreadPoolExecutor threadPoolExecutor, Handler handler, String allNum,
                         String chooseSize, int threadTask, List<Integer> numberList, boolean selected) {
        threadPoolExecutor.execute(new TicketRandomRunnable(handler, Integer.valueOf(allNum),
                Integer.valueOf(chooseSize), threadTask, numberList,selected));
        taskCount++;
    }


    @Override
    public void initTitleBar(BaseTitleBar titleBar) {
        titleBar.setTitleText("均值演算");
        ivRight = (ImageView) titleBar.right;
        ivRight.setImageResource(android.R.drawable.ic_dialog_info);
        ivRight.setOnClickListener(v -> ToastUtil.showToast("本页面模拟开奖,求数据平均值，后续将根据不同彩种的规则来做，" +
                "如果您是老彩民，需要加入一些特别的计算方式，请联系QQ 719243738"));
    }
}
