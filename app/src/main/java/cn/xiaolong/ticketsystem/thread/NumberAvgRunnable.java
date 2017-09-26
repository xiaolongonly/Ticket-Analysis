package cn.xiaolong.ticketsystem.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.standards.library.util.LogUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
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
    private List<List<Double>> mNumContainer;
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
        List<List<Double>> codeAvgList = new ArrayList<>();
        if (!isRepeat) {
            for (int count = 0; count < 100000; count++) {
                for (int i = 0; i < mRegulars.length; i++) {
                    String[] codeDis = mCodeDis[i].split("-");
                    for (int j = 0; j < Integer.valueOf(mRegulars[i]); j++) {
                        if (mNumContainer.size() <= i) {
                            mNumContainer.add(new ArrayList<>());
                        }
                        while (mNumContainer.get(i).size() < Integer.valueOf(mRegulars[i])) {
                            int number = numRandom(Integer.valueOf(codeDis[1]), Integer.valueOf(codeDis[0]));
                            if (!mNumContainer.get(i).contains(number)) {
                                mNumContainer.get(i).add((double) number);
                            }
                        }
                    }
                    Collections.sort(mNumContainer.get(i));

                }
                /**
                 * codeAvgListCalculate;
                 */
                for (int i = 0; i < mNumContainer.size(); i++) {
                    if (codeAvgList.size() <= i) {
                        codeAvgList.add(new ArrayList<>());
                    }
                    for (int j = 0; j < mNumContainer.get(i).size(); j++) {
                        if (codeAvgList.get(i).size() <= j) {
                            codeAvgList.get(i).add(mNumContainer.get(i).get(j));
                        } else {
                            codeAvgList.get(i).set(j, codeAvgList.get(i).get(j) + mNumContainer.get(i).get(j));
                        }
                    }
                }
                mNumContainer.clear();
            }
        } else {
            for (int i = 0; i < mRegulars.length; i++) {
                String[] codeDis = mCodeDis[i].split("-");
                for (int j = 0; j < Integer.valueOf(mRegulars[i]); j++) {
                    codeAvgList.get(i).add((Double.valueOf(codeDis[1]) + Double.valueOf(codeDis[0])) / 2);
                }
            }
        }
        for (int i = 0; i < codeAvgList.size(); i++) {
            for (int j = 0; j < codeAvgList.get(i).size(); j++) {
                codeAvgList.get(i).set(j, codeAvgList.get(i).get(j) / 100000);
            }
        }
        handMessage(codeAvgList);
    }

    private void handMessage(List<List<Double>> codeAvgList) {
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
