package cn.xiaolong.ticketsystem.utils;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.FuncN;

/**
 * <请描述这个类是干什么的>
 * 文本输入判断控制器
 * @data: 2016/7/12 16:35
 * @version: V1.0
 */
public class MergeEditUtil {

    public static Subscription getEnableStateSubscription(View enableView, EditText... editTexts) {
        return getLimitEnableStateSubscription(enableView, args -> {
            for (int i = 0; i < args.length; i++) {
                if (TextUtils.isEmpty(args[i].toString().trim())) {
                    return false;
                }
            }
            return true;
        }, editTexts);
    }

    public static Subscription getLimitEnableStateSubscription(View enableView, FuncN<Boolean> funcN, EditText... editTexts) {
        List<Observable<CharSequence>> observableList = new ArrayList<>();
        for (EditText editText : editTexts) {
            observableList.add(RxTextView.textChanges(editText));
        }

        return Observable.combineLatest(observableList, funcN)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isEnable -> enableView.setEnabled(isEnable), throwable -> enableView.setEnabled(false));
    }
}
