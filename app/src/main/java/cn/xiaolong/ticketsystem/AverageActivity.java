package cn.xiaolong.ticketsystem;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AverageActivity extends AppCompatActivity {
    private TextView tvGenerate;
    private TextView tvData;
    private EditText etAllNum;
    private EditText etChooseSize;
    private EditText etGenerateNum;

    private EditText etNumberEdit;
    private TextView tvAvg;
    private RecyclerView rvInitData;
    private List<Integer> numberList = new ArrayList<>();
    private NumberListAdapter numberListAdapter;
    private double avg;
    private int taskCount = 0;
//    private StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        avg = 0;
//        sb = new StringBuilder();
        etAllNum = (EditText) findViewById(R.id.etAllNum);
        etChooseSize = (EditText) findViewById(R.id.etChooseSize);
        etGenerateNum = (EditText) findViewById(R.id.etGenerateNum);
//        etTaskNum = (EditText) findViewById(R.id.etTaskNum);
        tvGenerate = (TextView) findViewById(R.id.tvGenerate);
        tvAvg = (TextView) findViewById(R.id.tvAvg);
        tvData = (TextView) findViewById(R.id.tvData);
        etNumberEdit = (EditText) findViewById(R.id.etNumberEdit);
        rvInitData = (RecyclerView) findViewById(R.id.rvInitData);


        numberListAdapter = new NumberListAdapter(this, numberList);
        rvInitData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvInitData.setAdapter(numberListAdapter);


        etNumberEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (TextUtils.isEmpty(etNumberEdit.getText())) {
                    return true;
                }
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (numberList.contains(Integer.valueOf(etNumberEdit.getText().toString()))) {
                        Toast.makeText(AverageActivity.this, "已经添加过该数据", Toast.LENGTH_LONG).show();
                        return true;
                    }
                    numberList.add(Integer.valueOf(etNumberEdit.getText().toString()));
                    numberListAdapter.notifyDataSetChanged();
                    etNumberEdit.setText("");
                    return true;
                }
                return false;
            }
        });
        tvGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGenerate.setEnabled(false);
                calculateData(numberList, TextUtils.isEmpty(etAllNum.getText().toString()) ? "0" : etAllNum.getText().toString(),
                        TextUtils.isEmpty(etChooseSize.getText().toString()) ? "0" : etChooseSize.getText().toString(),
                        TextUtils.isEmpty(etGenerateNum.getText().toString()) ? "0" : etGenerateNum.getText().toString());
            }
        });

    }

    private void calculateData(List<Integer> numberList, String allNum, String chooseSize, String generateSize) {
        final int cores = Math.max(1, Runtime.getRuntime().availableProcessors());
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(cores, cores, 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>(), new DefaultThreadFactory());
        Handler handler = getHandler(cores);
        int generateTaskSize = Integer.valueOf(generateSize);
        List<Integer> tasks = taskDivider(generateTaskSize, cores);
        //run all thread
        for (int i = 0; i < cores; i++) {
            runTask(threadPoolExecutor, handler, allNum, chooseSize, tasks.get(i), numberList);
        }
    }

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

    private Handler getHandler(final int cores) {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
//                sb.append(bundle.getString("resultStr"));
                avg += bundle.getDouble("avg");
                taskCount--;
                if (taskCount == 0) {
                    tvAvg.setText("平均值：" + (avg / cores));
                    avg = 0;
//                    sb = new StringBuilder();
                    taskCount = 0;
                    tvGenerate.setEnabled(true);
                }

            }
        };
        return handler;
    }

    private void runTask(ThreadPoolExecutor threadPoolExecutor, Handler handler, String allNum,
                         String chooseSize, int threadTask, List<Integer> numberList) {
        threadPoolExecutor.execute(new MyRunnable(handler, Integer.valueOf(allNum),
                Integer.valueOf(chooseSize), threadTask, numberList));
//        threadPoolExecutor.execute(new MyRunnable(handler));
        taskCount++;
    }


}
