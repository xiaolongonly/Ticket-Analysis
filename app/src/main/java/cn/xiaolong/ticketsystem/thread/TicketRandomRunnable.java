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

public class TicketRandomRunnable implements Runnable {
    public Handler mHandler;
    /**
     * 默认随机量/条
     */
    public static final int DEFAULT_TASK_SIZE = 1000000;
    /**
     * 默认选择数/个
     */
    public static final int DEFAULT_CHOOSE_SIZE = 7;

    /**
     * 36 选  7
     */
    public static final int DEFAULT_ALL_NUM = 36;

    private int randomNum;
    private int chooseSize;
    private int allNum;
    private Random random;
    private List<Integer> numContainer;
    private int defaultSize;
    private List<Integer> initData;
    private int defaultSum;

    public TicketRandomRunnable(Handler mHandler) {
        this(mHandler, DEFAULT_ALL_NUM, DEFAULT_CHOOSE_SIZE, DEFAULT_TASK_SIZE, new ArrayList<Integer>());
    }

    public TicketRandomRunnable(Handler handler, int allNum, int size, int generateTaskSize, List<Integer> currentNumber) {
        mHandler = handler;
        this.randomNum = (generateTaskSize == 0 ? DEFAULT_TASK_SIZE : generateTaskSize);
        this.chooseSize = size == 0 ? DEFAULT_CHOOSE_SIZE : size;
        this.allNum = allNum == 0 ? DEFAULT_ALL_NUM : allNum;
        random = new Random();
//        numContainer = deepCopy(currentNumber);
        numContainer = new ArrayList<>();
        initData = currentNumber;
        copyData(numContainer, currentNumber);
        defaultSize = currentNumber.size();
        defaultSum = getDefaultSum(currentNumber);

    }

    private int getDefaultSum(List<Integer> currentNumber) {
        int defaultSum = 0;
        for (int i : currentNumber) {
            defaultSum += i;
        }
        return defaultSum;
    }

    private void copyData(List<Integer> numContainer, List<Integer> currentNumber) {
        numContainer.clear();
        for (Integer i : currentNumber) {
            numContainer.add(i);
        }
    }


    @Override
    public void run() {
//        StringBuilder data = new StringBuilder("");
        double sum = 0;
        for (int l = 0; l < randomNum; l++) {
//            StringBuilder spanString = new StringBuilder("");
            sum += defaultSum;
            while (numContainer.size() < chooseSize) {
                int number = numRandom(allNum);
                if (!numContainer.contains(number)) {
                    numContainer.add(number);
                    sum += number;
//                    if (data.length() > 10000) {
//                        return;
//                    }
//                    spanString.append(number).append(",");
                }
            }
//            spanString = spanString.deleteCharAt(spanString.length() - 1).append("\n");
            if (defaultSize > 0) {
                copyData(numContainer, initData);
            } else {
                numContainer.clear();
            }
//            data.append(spanString);
        }
        handMessage(sum, "");

    }

    private void handMessage(double sum, String resultStr) {
        Bundle bundle = new Bundle();
        bundle.putString("resultStr", resultStr);
        bundle.putDouble("avg", sum / randomNum);
        Message message = new Message();
        message.setData(bundle);
        mHandler.sendMessage(message);
    }

    public int numRandom(int range) {
        return random.nextInt(range) + 1;
    }


    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }
}
