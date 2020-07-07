package com.yk.demo.wan.dmnd.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ScreenUtils;
import com.yk.demo.wan.android.R;


public class NetErrorDialog extends Dialog {
    private NetErrorDialogRefreshClickListener mRefreshClickListener;

    public NetErrorDialog(@NonNull Context context) {
        this(context, R.style.loading_dialog);
    }

    public NetErrorDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected NetErrorDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_net_error, null);
        setContentView(view);
        setCanceledOnTouchOutside(false);

        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        layoutParams.width = ScreenUtils.getScreenWidth();
        window.setAttributes(layoutParams);

    }

    public void onViewClicked() {
        if (mRefreshClickListener != null)
            mRefreshClickListener.onRefreshClick(this);
    }

    public void setRefreshClickListener(NetErrorDialogRefreshClickListener refreshClickListener) {
        mRefreshClickListener = refreshClickListener;
    }

    public static interface NetErrorDialogRefreshClickListener {
        void onRefreshClick(Dialog dialog);
    }
}
