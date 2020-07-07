package com.yk.demo.wan.dmnd.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.SizeUtils;
import com.yk.demo.wan.android.R;
import com.yk.demo.wan.dmnd.ui.dialog.UpdateDialog;

public class ProgressTextView extends TextView {

    private Paint mProgressPaint;
//    private Paint mBgPaintBlue;
    private int corius = SizeUtils.dp2px(41);
    private RectF mProgressRectF;
    private RectF mBgRectF;
    private int width, height;
    private float progress;
    private PorterDuffXfermode mPorterDuffXfermode;
    private int colorBlue;
    private int progressColor;

    public ProgressTextView(Context context) {
        this(context, null);
    }

    public ProgressTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void setProgress(int p) {
        this.progress = (float) p / 1000;
        if (progress < 1)
            setText(UpdateDialog.DOWNLOADING);
        else {
            setText(UpdateDialog.DOWNLOAD_OK);
        }
        invalidate();
    }

    private void initView(Context context) {
        colorBlue = ContextCompat.getColor(context, R.color.colorAccent);
        progressColor = 0xffF59C22;
//        mBgPaintBlue = new Paint(Paint.ANTI_ALIAS_FLAG);
////        mBgPaintBlue.setStyle(Paint.Style.FILL);
////        mBgPaintBlue.setColor(colorBlue);
        mBgRectF = new RectF(0, 0, 0, 0);

        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setStyle(Paint.Style.FILL);
//        mProgressPaint.setColor(0xffF59C22);
        mProgressRectF = new RectF(0, 0, (int) progress * width, height);
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
//        mProgressPaint.setXfermode(mPorterDuffXfermode);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        mProgressRectF.bottom = h;
        mBgRectF.bottom = h;
        mBgRectF.right = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //设置背景色
//        canvas.drawARGB(255, 139, 197, 186);
        int canvasWidth = getWidth();
        int canvasHeight = getHeight();
        int layerId = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);

        mProgressRectF.right = progress * width;
        mProgressPaint.setColor(colorBlue);
        canvas.drawRoundRect(mBgRectF, height, height, mProgressPaint);
        mProgressPaint.setColor(progressColor);
        mProgressPaint.setXfermode(mPorterDuffXfermode);
        canvas.drawRect(mProgressRectF, mProgressPaint);
        mProgressPaint.setXfermode(null);

        canvas.restoreToCount(layerId);
//        canvas.drawRoundRect(mRectF, height,height, mbgPaint);
        super.onDraw(canvas);
    }
}
