package com.yk.demo.wan.android.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.yk.demo.wan.android.R;

/**
 * 自定义ConstraintLayout
 * 其实就是用java画约束布局（简单实现）
 *
 * @creat by yk
 * 总结 先把子view添加完了再去clone
 * 最后在applyTo 部署
 */
public class CommonItemView extends ConstraintLayout {
    private TextView mLeftTextView;
    private TextView mRightTextView;
    private int mLeftTextColor;
    private int mRightTextColor;
    private int mMidTextColor;
    private int mMidHintColor;
    private String mLeftText;
    private String mRightText;
    private String mMidText;
    private int mLeftIconRes;
    private int mRightIconRes;
    private ConstraintSet mConstraintSet = new ConstraintSet();
    private float mLeftTextSize;
    private float mRightTextSize;
    private float mMidTextSize;
    private ImageView mRightImageView;
    private ImageView mLeftImageView;
    private int mRightIconTextMargin;
    private int mLeftTextMarginLeft;
    private int mRightTextMarginRight;
    private int mRightIconMarginRight;
    private int mLeftIconMarginLeft;
    private int mLeftIconMarginRight;
    private boolean mShowBottomLine;
    private View mLine;
    private Context mContext;
    private EditText mMidView;
    private int mMidTextMarginLeft;
    private int mMidTextMarginRight;
    private boolean isMidEdit;
    private boolean isMidRight;
    private String mMidHint;

    private InputType mInputType;
    private OnClickListener mMidOnClickListener;


    enum InputType {
        /**
         * 普通无限制
         */
        normal,
        /**
         * 限制11位手机号
         */
        phone,
        /**
         * 限制字母符号数字
         */
        pwd,
        /**
         * 限制6位支付密码
         */
        pwd_pay,
        /**
         * 不能输入
         */
        fixed

    }

    public CommonItemView(@NonNull Context context) {
        this(context, null);
    }

    public CommonItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonItemView);
        mLeftText = typedArray.getString(R.styleable.CommonItemView_pciv_left_text);
        mLeftTextColor = typedArray.getColor(R.styleable.CommonItemView_pciv_left_text_color, ContextCompat.getColor(context, R.color.c_66));
        mRightText = typedArray.getString(R.styleable.CommonItemView_pciv_right_text);
        mRightTextColor = typedArray.getColor(R.styleable.CommonItemView_pciv_right_text_color, ContextCompat.getColor(context, R.color.c_66));
        mLeftIconRes = typedArray.getResourceId(R.styleable.CommonItemView_pciv_left_icon, 0);
        mRightIconRes = typedArray.getResourceId(R.styleable.CommonItemView_pciv_right_icon, 0);
        mLeftTextSize = typedArray.getDimension(R.styleable.CommonItemView_pciv_left_text_size, SizeUtils.sp2px(14));
        mRightTextSize = typedArray.getDimension(R.styleable.CommonItemView_pciv_right_text_size, SizeUtils.sp2px(14));
        mRightIconTextMargin = (int) typedArray.getDimension(R.styleable.CommonItemView_pciv_right_icon_text_margin, SizeUtils.dp2px(10));
        mLeftTextMarginLeft = (int) typedArray.getDimension(R.styleable.CommonItemView_pciv_left_text_margin_left, SizeUtils.dp2px(10));
        mRightTextMarginRight = (int) typedArray.getDimension(R.styleable.CommonItemView_pciv_right_text_margin_right, SizeUtils.dp2px(10));
        mRightIconMarginRight = (int) typedArray.getDimension(R.styleable.CommonItemView_pciv_right_icon_margin_right, SizeUtils.dp2px(10));
        mLeftIconMarginLeft = (int) typedArray.getDimension(R.styleable.CommonItemView_pciv_left_icon_margin_left, SizeUtils.dp2px(10));
        mLeftIconMarginRight = (int) typedArray.getDimension(R.styleable.CommonItemView_pciv_left_icon_margin_right, SizeUtils.dp2px(10));
        mShowBottomLine = typedArray.getBoolean(R.styleable.CommonItemView_pciv_bottom_line, true);
        mMidText = typedArray.getString(R.styleable.CommonItemView_pciv_mid_text);
        mMidHint = typedArray.getString(R.styleable.CommonItemView_pciv_mid_hint);
        mMidTextSize = typedArray.getDimension(R.styleable.CommonItemView_pciv_mid_text_size, SizeUtils.sp2px(14));
        mMidTextColor = typedArray.getColor(R.styleable.CommonItemView_pciv_mid_text_color, ContextCompat.getColor(context, R.color.c_66));
        mMidHintColor = typedArray.getColor(R.styleable.CommonItemView_pciv_mid_hint_color, ContextCompat.getColor(context, R.color.c_90));
        mInputType = InputType.values()[typedArray.getInt(R.styleable.CommonItemView_pciv_mid_inputType, 0)];
        mMidTextMarginLeft = (int) typedArray.getDimension(R.styleable.CommonItemView_pciv_mid_text_margin_left, SizeUtils.dp2px(10));
        mMidTextMarginRight = (int) typedArray.getDimension(R.styleable.CommonItemView_pciv_mid_text_margin_right, SizeUtils.dp2px(10));
        isMidEdit = typedArray.getBoolean(R.styleable.CommonItemView_pciv_mid_is_edit, false);
        isMidRight = typedArray.getBoolean(R.styleable.CommonItemView_pciv_mid_is_right, false);
        typedArray.recycle();
        init();
    }

    public TextView getLeftTextView() {
        return mLeftTextView;
    }

    public void setLeftTextView(TextView leftTextView) {
        mLeftTextView = leftTextView;
    }

    public TextView getRightTextView() {
        return mRightTextView;
    }

    public void setRightTextView(TextView rightTextView) {
        mRightTextView = rightTextView;
    }

    public int getLeftTextColor() {
        return mLeftTextColor;
    }

    public void setLeftTextColor(int leftTextColor) {
        mLeftTextColor = leftTextColor;
        if (mLeftTextView != null)
            mLeftTextView.setTextColor(leftTextColor);
    }

    public int getRightTextColor() {
        return mRightTextColor;

    }

    public void setRightTextColor(int rightTextColor) {
        mRightTextColor = rightTextColor;
        if (mRightTextView != null)
            mRightTextView.setTextColor(rightTextColor);
    }

    public String getLeftText() {
        return mLeftText;
    }

    public void setLeftText(String leftText) {
        mLeftText = leftText;
        if (mLeftTextView != null)
            mLeftTextView.setText(leftText);
    }

    public String getRightText() {
        return mRightText;
    }

    public void setRightText(String rightText) {
        mRightText = rightText;
        if (mRightTextView != null)
            mRightTextView.setText(rightText);
    }

    public void setShowBottomLine(boolean showBottomLine) {
        mShowBottomLine = showBottomLine;
        if (mLine != null) {
            mLine.setVisibility(mShowBottomLine ? VISIBLE : INVISIBLE);
        }
    }

    public void setLeftIconRes(int leftIcon) {
        mLeftIconRes = leftIcon;
        if (mLeftImageView != null)
            mLeftImageView.setImageResource(mLeftIconRes);
    }

    public int getRightIconRes() {
        return mRightIconRes;
    }

    public void setRightIconRes(int rightIconRes) {
        mRightIconRes = rightIconRes;
        if (mRightImageView != null)
            mRightImageView.setImageResource(rightIconRes);
    }

    public float getLeftTextSize() {
        return mLeftTextSize;
    }

    public void setLeftTextSize(float leftTextSize) {
        mLeftTextSize = leftTextSize;
        if (mLeftTextView != null)
            mLeftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
    }

    public float getRightTextSize() {
        return mRightTextSize;
    }

    public void setRightTextSize(float rightTextSize) {
        mRightTextSize = rightTextSize;
        if (mRightTextView != null)
            mRightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
    }

    public ImageView getImageView() {
        return mRightImageView;
    }

    public void setImageView(ImageView imageView) {
        mRightImageView = imageView;
    }

    public int getLeftTextMarginLeft() {
        return mLeftTextMarginLeft;
    }

    public void setMidText(String midText) {
        mMidText = midText;
        if (mMidView != null)
            mMidView.setText(mMidText);
    }

    public EditText getMidView() {
        return mMidView;
    }

    public void setLeftTextMarginLeft(int leftTextMarginLeft) {
        mLeftTextMarginLeft = leftTextMarginLeft;
    }

    public int getRightTextMarginRight() {
        return mRightTextMarginRight;
    }

    public void setRightTextMarginRight(int rightTextMarginRight) {
        mRightTextMarginRight = rightTextMarginRight;
    }

    public String getMidText() {
        if (mMidView != null) {
            return mMidView.getText().toString().trim();
        }
        return "";
    }

    public void init() {
        setBackgroundResource(R.drawable.selector_common_press_bg);
        if (!TextUtils.isEmpty(mLeftText)) {
            mLeftTextView = new TextView(mContext);
            mLeftTextView.setId(R.id.left_text_view);
            mLeftTextView.setTextColor(mLeftTextColor);
            mLeftTextView.setText(mLeftText);
            mLeftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftTextSize);
            addView(mLeftTextView);
        }

        if (!TextUtils.isEmpty(mMidText) || !TextUtils.isEmpty(mMidHint)) {
            mMidView = new EditText(mContext);
            mMidView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_transparent));
            mMidView.setEnabled(isMidEdit);
            mMidView.setSingleLine();
            mMidView.setId(R.id.mid_text_view);
            mMidView.setTextColor(mMidTextColor);
            if (!TextUtils.isEmpty(mMidText))
                mMidView.setText(mMidText.trim());
            if (!TextUtils.isEmpty(mMidHint))
                mMidView.setHint(mMidHint);
            mMidView.setHintTextColor(mMidHintColor);
            mMidView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mMidTextSize);


            switch (mInputType) {
                case pwd:
                    mMidView.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                    break;
                case phone:
                    mMidView.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                    InputFilter.LengthFilter lengthFilter = new InputFilter.LengthFilter(11);
                    mMidView.setFilters(new InputFilter[]{lengthFilter});
                    break;
                case pwd_pay:
                    mMidView.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                    InputFilter.LengthFilter lengthFilter2 = new InputFilter.LengthFilter(6);
                    mMidView.setFilters(new InputFilter[]{lengthFilter2});
                    break;
                case fixed:
                    mMidView.setEnabled(false);
                    break;
                case normal:
                    break;
                default:
                    break;
            }

            addView(mMidView);
            mMidView.getLayoutParams().width = 0;
            mMidView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            mMidView.setGravity(Gravity.CENTER_VERTICAL);
        }

        if (!TextUtils.isEmpty(mRightText)) {
            mRightTextView = new TextView(mContext);
            mRightTextView.setId(R.id.right_text_view);
            mRightTextView.setTextColor(mRightTextColor);
            mRightTextView.setText(mRightText);
            mRightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize);
            addView(mRightTextView);
            mRightTextView.getLayoutParams().width = ScreenUtils.getAppScreenWidth() / 2;
            mRightTextView.setGravity(Gravity.END);
            mRightTextView.setSingleLine();
            mRightTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            mRightTextView.setMarqueeRepeatLimit(-1);
            mRightTextView.setSelected(true);
        }

        if (mLeftIconRes != 0) {
            mLeftImageView = new ImageView(mContext);
            mLeftImageView.setId(R.id.left_image_view);
            mLeftImageView.setImageResource(mLeftIconRes);
            addView(mLeftImageView);
        }

        if (mRightIconRes != 0) {
            mRightImageView = new ImageView(mContext);
            mRightImageView.setId(R.id.right_image_view);
            mRightImageView.setImageResource(mRightIconRes);
            addView(mRightImageView);
        }

        if (mShowBottomLine) {
            mLine = new View(mContext);
            mLine.setId(R.id.civ_bottom_line);
            mLine.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(0.667f)));
            mLine.setBackgroundColor(ContextCompat.getColor(mContext, R.color.c_ef));
            addView(mLine);
        }
        mConstraintSet.clone(this);//确保所有的子view已经添加完毕

        if (mLeftImageView != null) {
            mConstraintSet.centerVertically(mLeftImageView.getId(), ConstraintSet.PARENT_ID);
            mConstraintSet.connect(mLeftImageView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, mLeftIconMarginLeft);
        }

        if (mLeftTextView != null) {
            if (mLeftImageView != null) {
                mConstraintSet.centerVertically(mLeftTextView.getId(), ConstraintSet.PARENT_ID);
                mConstraintSet.connect(mLeftTextView.getId(), ConstraintSet.LEFT, mLeftImageView.getId(), ConstraintSet.RIGHT, mLeftTextMarginLeft);
            } else {
                mConstraintSet.centerVertically(mLeftTextView.getId(), ConstraintSet.PARENT_ID);
                mConstraintSet.connect(mLeftTextView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, mLeftTextMarginLeft);
            }
        }

        if (mMidView != null) {
            mConstraintSet.centerVertically(mMidView.getId(), ConstraintSet.PARENT_ID);
            if (isMidRight) {
                mMidView.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            }
            mConstraintSet.connect(mMidView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, mMidTextMarginLeft);
            mConstraintSet.connect(mMidView.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, mMidTextMarginRight);
        }

        if (mRightImageView != null) {
            mConstraintSet.centerVertically(mRightImageView.getId(), ConstraintSet.PARENT_ID);
            mConstraintSet.connect(mRightImageView.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, mRightIconMarginRight);
            if (mRightTextView != null) {
                mConstraintSet.centerVertically(mRightTextView.getId(), ConstraintSet.PARENT_ID);
                mConstraintSet.connect(mRightTextView.getId(), ConstraintSet.RIGHT, mRightImageView.getId(), ConstraintSet.LEFT, mRightIconTextMargin);
            }
        } else {
            if (mRightTextView != null) {
                mConstraintSet.centerVertically(mRightTextView.getId(), ConstraintSet.PARENT_ID);
                mConstraintSet.connect(mRightTextView.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, mRightTextMarginRight);
            }
        }
        if (mLine != null)
            mConstraintSet.connect(mLine.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

        mConstraintSet.applyTo(this);
    }
}
