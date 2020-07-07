package com.yk.demo.wan.sudoku.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import com.blankj.utilcode.util.SizeUtils
import com.yk.demo.wan.sudoku.entity.UnitBean

class UnitView : View {
    var unitBean: UnitBean = UnitBean()
    var mHeight = 0
    var mWidth = 0
    var textX: Float = 0.0f
    var textY: Float = 0.0f
    private val confirmColor = Color.parseColor("#004080")
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context, unitBean: UnitBean) : this(context, null, unitBean)
    constructor(context: Context, attrs: AttributeSet?, unitBean: UnitBean)
            : this(context, attrs, 0, unitBean)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, unitBean: UnitBean)
            : super(context, attrs, defStyleAttr) {
        initView(unitBean)
    }

    private fun initView(unit: UnitBean) {
        this.unitBean = unit
        mPaint.textSize = SizeUtils.sp2px(20F).toFloat()
        mPaint.color = confirmColor
//        if (unitBean.isConfirm()) {
//
//        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mHeight = bottom - top
        mWidth = right - left

    }


    override fun onDraw(canvas: Canvas?) {
        if (unitBean.isConfirm())
            canvas?.drawText(unitBean.confirmNum.toString(), textX, textY, mPaint)
    }


}