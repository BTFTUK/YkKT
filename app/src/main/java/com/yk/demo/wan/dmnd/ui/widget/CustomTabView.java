package com.yk.demo.wan.dmnd.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.Utils;
import com.yk.demo.wan.android.R;
import com.yk.demo.wan.android.utils.DmndUtils;

public class CustomTabView extends FrameLayout {

    private TextView mTvTitle;
    private ImageView mIcon;
    private int selectedIcon;
    private int normalIcon;
    private int selectedColor = ContextCompat.getColor(Utils.getApp(),R.color.colorAccent);
    private int normalColor = ContextCompat.getColor(Utils.getApp(),R.color.c_a432);

    public CustomTabView(Context context) {
        this(context, null);
    }

    public CustomTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View rootView = inflate(context, R.layout.custom_tab_view, this);
        mTvTitle = rootView.findViewById(R.id.tv_title);
        mIcon = rootView.findViewById(R.id.iv_icon);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        mTvTitle.setTextColor(selected ? selectedColor : normalColor);
        mIcon.setImageResource(selected ? selectedIcon : normalIcon);
//        mTvNum.setAlpha(selected ? 1 : 0.6f);
    }

    public void setTvTitle(String s) {
        mTvTitle.setText(s);
    }

    public void setIcon(int icon) {
        if (icon != 0) {
            mIcon.setImageResource(icon);
        }
    }

    public void setIcons(int selectedIcon, int normalIcon) {
        this.selectedIcon = selectedIcon;
        this.normalIcon = normalIcon;
    }
}
