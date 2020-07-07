package com.yk.demo.wan.dmnd.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ScreenUtils;
import com.yk.demo.wan.android.R;
import com.yk.demo.wan.dmnd.ui.widget.ProgressTextView;

public class UpdateDialog extends Dialog {

    public static final String DOWNLOAD_OK = "下载完成";
    public static final String UPDATE_NOW = "马上更新";
    public static final String DOWNLOADING = "下载中";

    private TextView mTvContent;

    private ProgressTextView mTvConfirm;
    private UpdateClickListener mConfirmListener;

    public UpdateDialog(@NonNull Context context) {
        this(context, R.style.ad_dialog);
    }

    public UpdateDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected UpdateDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    public void setConfirmListener(UpdateClickListener confirmListener) {
        mConfirmListener = confirmListener;
    }

    private void init(Context context) {
        View rootView = View.inflate(context, R.layout.dialog_update, null);
        setContentView(rootView);

        Window window = getWindow();

        assert window != null;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        layoutParams.width = (int) (ScreenUtils.getScreenWidth() * 0.7);
        window.setAttributes(layoutParams);

        mTvContent = rootView.findViewById(R.id.tv_content);
        mTvConfirm = rootView.findViewById(R.id.tv_confirm);

        rootView.findViewById(R.id.iv_close)
                .setOnClickListener(v -> dismiss());

        mTvConfirm.setOnClickListener(v -> {
            if (mConfirmListener != null
                    && !TextUtils.isEmpty(mTvConfirm.getText()))
                mConfirmListener.onClick(mTvConfirm.getText().toString());
        });
    }

    public void updateProgress(String text, int percent) {
        if (mTvConfirm != null) {
            mTvConfirm.setProgress(percent);
        }
    }

    public void setUpdateContent(String s) {
        mTvContent.setText(s);
    }

    public void setTvConfirm(String s) {
        mTvConfirm.setText(s);
    }

    public interface UpdateClickListener {
        void onClick(String text);
    }
}
