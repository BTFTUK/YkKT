package com.yk.demo.wan.dmnd.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.yk.demo.wan.android.R;


public class NetErrorView extends FrameLayout {
    Button mBtnRefresh;
    ImageView mIvNoNet;
    ConstraintLayout mClNetError;
    private OnRefreshListener mOnRefreshListener;

    public NetErrorView(@NonNull Context context) {
        this(context, null);
    }

    public NetErrorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NetErrorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        View view = View.inflate(context, R.layout.layout_net_error, this);
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
    }

    public void onViewClicked() {
        if (mOnRefreshListener != null)
            mOnRefreshListener.onRefresh();
    }

    public interface OnRefreshListener {
        void onRefresh();
    }


}
