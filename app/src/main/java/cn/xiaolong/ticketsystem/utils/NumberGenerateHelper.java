package cn.xiaolong.ticketsystem.utils;

import com.standards.library.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.xiaolong.ticketsystem.bean.TicketRegular;
import rx.Observable;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/22 17:04
 */

public class NumberGenerateHelper {
    private Random random;
    private String[] regulars;
    private String[] codeDis;
    private Boolean repeat;

    public NumberGenerateHelper(TicketRegular ticketRegular) {
        random = new Random();
        regulars = ticketRegular.regular.split("\\+");
        codeDis = ticketRegular.codeDis.split(",");
        repeat = ticketRegular.repeat;
    }

    public int numRandom(int range, int start) {
        return random.nextInt(range + (repeat ? 1 : 0)) + start;
    }


    public Observable<String> generateNumberGroup(List<List<String>> numberBase) {
        List<String> baseCode = new ArrayList<>();

        int codeSize = 0;
        for (String str : regulars) {
            codeSize += Integer.valueOf(str);
        }
        if (numberBase == null) {
            for (int i = 0; i < codeSize; i++) {
                baseCode.add("X");
            }
        } else {
            for (List<String> codeLiet : numberBase) {
                for (String code : codeLiet) {
                    baseCode.add(code.equals("") ? "X" : code);
                }
            }
        }
        List<List<String>> baseCodeList = new ArrayList<>();
        int count = 0;
        for (String regularStr : regulars) {
            baseCodeList.add(baseCode.subList(count, count += Integer.valueOf(regularStr)));
        }

        String result = "";
        for (int i = 0; i < baseCodeList.size(); i++) {
            String[] codeRange;
            if (codeDis.length > i) {
                codeRange = codeDis[i].split("-");
            } else {
                codeRange = codeDis[0].split("-");
            }
            for (int j = 0; j < baseCodeList.get(i).size(); j++) {
                while (baseCodeList.get(i).get(j).equals("X")) {
                    int num = numRandom(Integer.valueOf(codeRange[1]), Integer.valueOf(codeRange[0]));
                    if (repeat || !baseCode.contains(num + "")) {
                        baseCodeList.get(i).set(j, num + "");
                    }
                }
                result += baseCodeList.get(i).get(j) + ",";
            }
            result = result.substring(0, result.length() - 1);
            result += "+";
        }
        LogUtil.d(result);
        return Observable.just(result.substring(0, result.length() - 1));
    }
}
