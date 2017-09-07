package com.standards.library.listview.loading;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.standards.library.R;

public class ProgressDialog extends Dialog {

    private TextView tvProgress;
    private String progressText;
    boolean cancelAble;

    protected ProgressDialog(Context context) {
        super(context, R.style.ProgressDialogTheme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_progress);
        tvProgress = (TextView) findViewById(R.id.text);
        setLoadText(progressText);
        setCancelable(cancelAble);
    }

    public void setLoadText(String text) {
        tvProgress.setVisibility((TextUtils.isEmpty(text) || TextUtils.isEmpty(text.trim())) ? View.GONE : View.VISIBLE);
        if(!TextUtils.isEmpty(text)) {
            tvProgress.setText(text);
        }
    }

    public static ProgressDialog show(Context context) {
        return show(context, null);
    }


    public static ProgressDialog show(Context context, String progressText) {
        return show(context, progressText, false);
    }

    public static ProgressDialog show(Context context, String progressText, boolean cancelAble) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.progressText = progressText;
        dialog.cancelAble = cancelAble;
        dialog.show();
        return dialog;
    }

}
