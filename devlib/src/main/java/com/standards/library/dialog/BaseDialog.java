package com.standards.library.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.standards.library.R;
import com.zhy.autolayout.utils.AutoUtils;

public abstract class BaseDialog extends AlertDialog implements View.OnClickListener {
    protected Context mContext;
    private boolean closeOutside;

    protected BaseDialog(Context context) {
        this(context, true);
    }

    protected BaseDialog(Context context, boolean closeOutside) {
        super(context, R.style.fullscreen_dialog);
        this.closeOutside = closeOutside;
        this.mContext = context;
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        LayoutParams clp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        View content = getContentView(layoutInflater);
        initView(content);
        View layout = getBaseDialogView(layoutInflater);
        AutoUtils.autoSize(layout); //适配

        layout.findViewById(R.id.dialog_top).setOnClickListener(this);
        layout.findViewById(R.id.dialog_bottom).setOnClickListener(this);

        LinearLayout contentLayout = (LinearLayout) layout.findViewById(R.id.dialog_content);
        contentLayout.addView(content, clp);

        setContentView(layout, clp);
        setCancelable(true);
        setCanceledOnTouchOutside(true);


        WindowManager.LayoutParams lParams = getWindow().getAttributes();
        lParams.gravity = Gravity.CENTER;
        lParams.width = LayoutParams.MATCH_PARENT;
        lParams.height = LayoutParams.MATCH_PARENT;
        lParams.alpha = 1.0f;
        lParams.dimAmount = 0.0f;
        getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        getWindow().setAttributes(lParams);
    }

    public View getBaseDialogView(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.dialog_layout, null);
    }

    @Override
    public void onClick(View v) {
        if (closeOutside) {
            this.dismiss();
        }
    }

    public abstract View getContentView(LayoutInflater inflater);

    protected abstract void initView(View content);
}
