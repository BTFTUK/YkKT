package com.yk.demo.wan.dmnd.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yk.demo.wan.android.R;


public class CommonSwipeRefreshLayout extends SwipeRefreshLayout {
    private Context mContext;
    public CommonSwipeRefreshLayout(@NonNull Context context) {
        this(context,null);
    }

    public CommonSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setColorSchemeColors(ContextCompat.getColor(mContext, R.color.colorAccent));
    }
}
