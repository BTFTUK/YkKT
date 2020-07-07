package com.yk.demo.wan.android.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.yk.demo.wan.android.R;


public class TopBarView extends FrameLayout {
    TextView mPublicTvTitle;
    TextView mPublicTvRight;
    ImageView mPublicIvLeft;
    ImageView mPublicIvRight;
    private String mRightText;
    private String mTitle;
    private int mTvMidTextColor;
    private int mTbvLeftIcon;
    private int mTbvRightIcon;
    private int mBgColor;
    private boolean mHideLeft;


    private OnClickListener leftClick;
    private OnClickListener rightClick;
    private OnClickListener rightIvClick;

    public void setLeftIconClickListener(OnClickListener leftIconClickListener) {
        this.leftClick = leftIconClickListener;
    }

    public void setRightTextClickListener(String text, OnClickListener listener) {
        this.mRightText = text;
        this.rightClick = listener;
        if (mPublicTvRight != null)
            mPublicTvRight.setText(mRightText);
    }

    public void setRightTextClickListener(OnClickListener listener) {
        this.rightClick = listener;
        if (mPublicTvRight != null)
            mPublicTvRight.setText(mRightText);
    }

    public void setRightText(String text) {
        this.mRightText = text;
        if (mPublicTvRight != null)
            mPublicTvRight.setText(mRightText);
    }

    public void setRightIconClickListener(OnClickListener listener) {
        this.rightIvClick = listener;
    }

    public TopBarView(Context context) {
        this(context, null);
    }

    public TopBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBarView);
        mTbvLeftIcon = typedArray.getResourceId(R.styleable.TopBarView_tbv_left_icon, R.mipmap.icon_back);
        mTbvRightIcon = typedArray.getResourceId(R.styleable.TopBarView_tbv_right_icon, 0);
        mTitle = typedArray.getString(R.styleable.TopBarView_tbv_middle_text);
        mTvMidTextColor = typedArray.getColor(R.styleable.TopBarView_tbv_middle_text_color, ContextCompat.getColor(context, R.color.black));
        mBgColor = typedArray.getColor(R.styleable.TopBarView_tbv_bg_color, ContextCompat.getColor(context, R.color.white));
        mHideLeft = typedArray.getBoolean(R.styleable.TopBarView_tbv_hide_left, false);
        typedArray.recycle();
        init(context);
    }

    private void init(Context context) {
        View mRootView = View.inflate(context, R.layout.top_bar, this);
        setBackgroundColor(mBgColor);
        mPublicTvTitle = mRootView.findViewById(R.id.public_tv_title);
        mPublicIvLeft = mRootView.findViewById(R.id.public_iv_left);
        mPublicTvRight = mRootView.findViewById(R.id.public_tv_right);
        mPublicIvRight = mRootView.findViewById(R.id.public_iv_right);
        drawView();
        mPublicIvLeft.setOnClickListener(v -> {
            if (leftClick != null) {
                leftClick.onClick(v);
            } else {
                if (getContext() instanceof Activity) {
                    Activity activity = (Activity) getContext();
                    activity.finish();
                }
            }
        });

        mPublicTvRight.setOnClickListener(v -> {
            if (rightClick != null) {
                rightClick.onClick(v);
            }
        });

        mPublicIvRight.setOnClickListener(v -> {
            if (rightIvClick != null) {
                rightIvClick.onClick(v);
            }
        });
    }

    private void drawView() {
        if (TextUtils.isEmpty(mTitle)) {
            mPublicTvTitle.setVisibility(GONE);
        } else {
            mPublicTvTitle.setVisibility(VISIBLE);
            mPublicTvTitle.setText(mTitle);
        }
        if (mPublicTvTitle.getVisibility() == View.VISIBLE) {
            mPublicTvTitle.setTextColor(mTvMidTextColor);
        }
        if (mTbvLeftIcon == 0 || mHideLeft) {
            mPublicIvLeft.setVisibility(GONE);
        } else {
            mPublicIvLeft.setVisibility(VISIBLE);
            mPublicIvLeft.setImageResource(mTbvLeftIcon);
        }

        if (mTbvRightIcon == 0)
            mPublicIvRight.setVisibility(GONE);
        else {
            mPublicIvRight.setVisibility(VISIBLE);
            mPublicIvRight.setImageResource(mTbvRightIcon);
        }
    }

    public void setTitle(String title) {
        this.mTitle = title;
        if (mPublicTvTitle != null)
            mPublicTvTitle.setText(mTitle);
    }

    public String getTitleText() {
        if (mPublicTvTitle != null)
            return mPublicTvTitle.getText().toString();
        else
            return "";
    }

    public int getLeftIcon() {
        return mTbvLeftIcon;
    }

    public void setLeftIcon(int tbvLeftIcon) {
        mTbvLeftIcon = tbvLeftIcon;
        if (mPublicIvLeft != null) {
            if (mTbvLeftIcon != 0) {
                mPublicIvLeft.setVisibility(VISIBLE);
                mPublicIvLeft.setImageResource(mTbvLeftIcon);
            } else {
                mPublicIvLeft.setVisibility(GONE);
            }
        }
    }

    private void hideLeftIcon() {
        if (mPublicIvLeft != null)
            mPublicIvLeft.setVisibility(GONE);
    }
}
