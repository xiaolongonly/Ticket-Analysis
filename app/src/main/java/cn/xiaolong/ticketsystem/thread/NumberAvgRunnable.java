package cn.xiaolong.ticketsystem.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/5 10:13
 */

public class NumberAvgRunnable implements Runnable {
    public Handler mHandler;
    private String[] mRegulars;
    private String[] mCodeDis;
    private Random mRandom;
    private List<Integer> mNumContainer;
    private boolean isRepeat;


    public NumberAvgRunnable(Handler handler, String regulars, String ranges, boolean isRepeat) {
        mHandler = handler;
        mRegulars = regulars.split("\\+");
        mCodeDis = ranges.split(",");
        if (mCodeDis.length < mRegulars.length) {
            mCodeDis = new String[]{mCodeDis[0], mCodeDis[0]};
        }
        this.isRepeat = isRepeat;
        mNumContainer = new ArrayList<>();
        mRandom = new Random();
    }

    @Override
    public void run() {
        List<Double> codeAvgList = new ArrayList<>();
        if (!isRepeat) {
            for (int i = 0; i < mRegulars.length; i++) {
                String[] codeDis = mCodeDis[i].split("-");
                for (int j = 0; j < Integer.valueOf(mRegulars[i]); j++) {
                    if (codeAvgList.size() > j) {
                        codeAvgList.set(j, codeAvgList.get(j) + numRandom(Integer.valueOf(codeDis[1]), Integer.valueOf(codeDis[0])));
                    } else {
                        codeAvgList.add(numRandom(Integer.valueOf(codeDis[1]), Integer.valueOf(codeDis[0])));
                    }
                }
            }
        } else {
            for (int i = 0; i < mRegulars.length; i++) {
                String[] codeDis = mCodeDis[i].split("-");
                for (int j = 0; j < Integer.valueOf(mRegulars[i]); j++) {
                    codeAvgList.add((Double.valueOf(codeDis[1]) + Double.valueOf(codeDis[0])) / 2);
                }
            }
        }


        handMessage(codeAvgList);
    }

    private void handMessage(List<Double> codeAvgList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("codeAvgList", (ArrayList) codeAvgList);
        Message message = new Message();
        message.setData(bundle);
        mHandler.sendMessage(message);
    }

    public int numRandom(int range, int start) {
        return mRandom.nextInt(range) + start;
    }

}
