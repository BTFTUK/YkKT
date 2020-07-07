package com.yk.demo.wan.sudoku.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.blankj.utilcode.util.SizeUtils
import com.yk.demo.wan.sudoku.entity.UnitBean
import java.util.ArrayList

class GirdView : View {
    var girdNum = 9
    lateinit var array: Array<UnitBean?>
    lateinit var arrayX: Array<Array<UnitBean?>?>
    lateinit var arrayY: Array<Array<UnitBean?>?>
    lateinit var arrayZ: Array<Array<UnitBean?>?>
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val confirmColor = Color.parseColor("#004080")
    private val noteSelectColor = Color.parseColor("#00C400")
    private val bgColorConfirm = 0x50000040
    private val bgColorConfirmOther: Int = Color.parseColor("#90000040")
    private val bgColorNor: Int = 0x50008080
    private val bgColorNorSelect: Int = Color.parseColor("#90008080")
    private val bgColorError: Int = Color.parseColor("#90FF0000")
    private val boldLine = SizeUtils.dp2px(3F).toFloat()
    private val norLine = SizeUtils.dp2px(1F).toFloat()
    private var mTop = 0.0F
    private var mBottom = 0.0F
    private var mLeft = 0.0F
    private var mRight = 0.0F
    private var drawLineX = 0.0F
    private var drawLineY = 0.0F
    private var split = 0.0F
    private var rectF = RectF()
    private var curSelectIndex = 0
    private var curSelectUnitBean: UnitBean? = null
    private var linkX = 0
    private var linkY = 0
    private var linkZ = 0
    var showNote = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        val totalNum = girdNum * girdNum
        array = arrayOfNulls(totalNum)

        initXYZArray()
        var i = 0
        while (i < totalNum) {
            val unitBean = UnitBean()
            val x = i % girdNum + 1 //第几排
            val y = i / girdNum + 1 //第几列
            var z = 0
            when (x) {
                in 1..3 -> {
                    when (y) {
                        in 1..3 -> {
                            z = 1
                        }
                        in 4..6 -> {
                            z = 2
                        }
                        in 7..9 -> {
                            z = 3
                        }
                    }
                }
                in 4..6 -> {
                    when (y) {
                        in 1..3 -> {
                            z = 4
                        }
                        in 4..6 -> {
                            z = 5
                        }
                        in 7..9 -> {
                            z = 6
                        }
                    }

                }
                in 7..9 -> {
                    when (y) {
                        in 1..3 -> {
                            z = 7
                        }
                        in 4..6 -> {
                            z = 8
                        }
                        in 7..9 -> {
                            z = 9
                        }
                    }
                }
            }
            unitBean.x = x
            unitBean.y = y
            unitBean.z = z
            unitBean.index = (x - 1) * (girdNum) + y - 1  //得到在数组的下标
            if (unitBean.index < array.size) {
                array[unitBean.index] = unitBean
                putXYZ(unitBean)
            }
            i++
        }
        array[curSelectIndex]?.selected = true
        curSelectUnitBean = array[0]
    }

    private fun putXYZ(unitBean: UnitBean) {
        val x = unitBean.x
        val y = unitBean.y
        val z = unitBean.z
        arrayX[x - 1]?.set(y - 1, unitBean)
        arrayY[y - 1]?.set(x - 1, unitBean)
        val indexZ = (x - 1) % 3 + (((y - 1) % 3) * 3)
        if (indexZ == -1) {
            return
        }
        arrayZ[z - 1]?.set(indexZ, unitBean)
    }

    private fun initXYZArray() {
        arrayX = arrayOfNulls(girdNum)
        arrayY = arrayOfNulls(girdNum)
        arrayZ = arrayOfNulls(girdNum)
        for (i in 0 until girdNum) {
            arrayX[i] = arrayOfNulls(girdNum)
            arrayY[i] = arrayOfNulls(girdNum)
            arrayZ[i] = arrayOfNulls(girdNum)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mLeft = left.toFloat()
        mRight = right.toFloat()
        mTop = top.toFloat()
        mBottom = bottom.toFloat()
        split = (mRight - mLeft) / girdNum
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawSplitLine(canvas)
        drawGridBg(canvas)
    }

    //画方格背景
    private fun drawGridBg(canvas: Canvas?) {
        paint.style = Paint.Style.FILL
        for (unitBean in array) {
            unitBean?.let {
                if (it.error) {
                    paint.color = bgColorError
                    canvas?.drawRect(it.rectF, paint)
                } else if (curSelectUnitBean != null && it.isLinked(curSelectUnitBean!!)) {
                    if (it.selected) {
                        paint.color = if (it.isConfirm()) bgColorConfirm else bgColorNorSelect
                    } else {
                        paint.color = bgColorNor
                    }
                    canvas?.drawRect(it.rectF, paint)
                } else if (it.isConfirm() && it.confirmNum == curSelectUnitBean?.confirmNum) {
                    paint.color = bgColorConfirmOther
                    canvas?.drawRect(it.rectF, paint)
                } else {

                }
            }
        }
    }

    //画确定文字
    private fun drawConfirmText(canvas: Canvas?, rectF: RectF, text: String) {
        paint.textAlign = Paint.Align.CENTER
        paint.color = confirmColor
        paint.style = Paint.Style.FILL
        paint.textSize = SizeUtils.sp2px(20F).toFloat()

        val fontMetrics: Paint.FontMetrics = paint.fontMetrics
        val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        val baseline: Float = rectF.centerY() + distance
        canvas!!.drawText(text, rectF.centerX(), baseline, paint)
        paint.color = Color.parseColor("#152B22")
//        canvas.drawRect(rectF, paint)
    }

    //画笔记文字
    private fun drawNoteTextReal(canvas: Canvas?, rectF: RectF, text: String) {
        val fontMetrics: Paint.FontMetrics = paint.fontMetrics
        val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        val baseline: Float = rectF.centerY() + distance
        canvas!!.drawText(text, rectF.centerX(), baseline, paint)
//        canvas.drawRect(rectF, paint)
//        paint.color = Color.parseColor("#152B22")
    }

    //画笔记文字
    private fun drawNoteText(canvas: Canvas?, rectF: RectF, notes: Array<Int>) {
        if (!showNote)
            return
//        paint.color = Color.parseColor("#004080")
        paint.textSize = SizeUtils.sp2px(8F).toFloat()
        val rectFs = getNineGrid(rectF)
        if (rectFs.size != notes.size)
            return
        for (i in rectFs.indices) {
            val smallRectF = rectFs[i]
            if (notes[i] != 0 && smallRectF != null) {
                curSelectUnitBean?.let {
                    paint.color =
                        if (notes[i] == it.confirmNum) noteSelectColor
                        else confirmColor
                    drawNoteTextReal(canvas, smallRectF, notes[i].toString())
                }
            }
        }
    }

    //画分割线
    private fun drawSplitLine(canvas: Canvas?) {
        paint.color = confirmColor
        paint.style = Paint.Style.FILL
        for (i in 0..9) {
            drawLineX = split * i + mLeft
            drawLineY = split * i + mTop
            if (i % 3 == 0) {
                paint.strokeWidth = boldLine
            } else {
                paint.strokeWidth = norLine
            }
            canvas?.drawLine(drawLineX, mTop, drawLineX, mBottom, paint)
            canvas?.drawLine(mLeft, drawLineY, mRight, drawLineY, paint)
        }

        for (unitBean in array) {
            if (unitBean == null)
                continue
            if (unitBean.rectF.bottom != 0.0f) {
                rectF.top = unitBean.rectF.top
                rectF.bottom = unitBean.rectF.bottom
                rectF.left = unitBean.rectF.left
                rectF.right = unitBean.rectF.right
            } else {
                rectF.top = rectFtop(unitBean)
                rectF.bottom = rectF.top + split
                rectF.left = rectFLeft(unitBean)
                rectF.right = rectF.left + split

                unitBean.rectF.top = rectF.top
                unitBean.rectF.bottom = rectF.bottom
                unitBean.rectF.left = rectF.left
                unitBean.rectF.right = rectF.right
            }
            if (unitBean.isConfirm()) {
                drawConfirmText(canvas, rectF, unitBean.confirmNum.toString())
            } else {
                drawNoteText(canvas, rectF, unitBean.noteNum)
            }
        }

    }

    fun rectFtop(unitBean: UnitBean): Float {
        return mTop + unitBean.getRectSplitY(split)
    }

    fun rectFLeft(unitBean: UnitBean): Float {
        return mLeft + unitBean.getRectSplitX(split)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null)
            return super.onTouchEvent(event)
        val x = event.x
        val y = event.y
        chooseGrid(x, y)
        return true
    }

    //选中某一个方格处理
    private fun chooseGrid(x: Float, y: Float) {
        curSelectUnitBean?.let {
            if (!it.rectF.contains(x, y)) {
                for (i in array.indices) {
                    val unitBean = array[i] ?: return
                    if (unitBean.rectF.contains(x, y)) {
                        unitBean.selected = true
                        array[curSelectIndex]?.selected = false
                        curSelectIndex = i
                        curSelectUnitBean = unitBean
                        linkX = unitBean.x
                        linkY = unitBean.y
                        linkZ = unitBean.z
                        curSelectUnitBean?.let { it1 ->
                            note(it1)
                        }
                        invalidate()
                        return
                    }
                }
            }
        }
    }

    private fun note(unitBean: UnitBean) {
        val notes = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        for (item in array) {
            item?.let {
                if (unitBean.isLinkedNotContains(it) && it.isRealConfirm()) {
                    notes[it.confirmNum - 1] = 0
                }
            }
        }
        for (i in notes.indices) {
            unitBean.noteNum[i] = if (unitBean.isRealConfirm()) 0 else notes[i]
        }
    }

    //全部笔记
    fun noteAll() {
        for (item in array) {
            item?.let {
                note(it)
            }
        }
        invalidate()
    }

    //设置已经确认的数据
    fun setConfirmText(num: Int) {
        array[curSelectIndex]?.let {
            if (it.isRealConfirm()) {
                if (num == 0) {
                    it.confirmNum = num
                    it.error = false
                    noteAll()
                }
            } else {
                it.confirmNum = num
                it.error = it.isConfirm()
                for (i in it.noteNum) {
                    if (i == num) {
                        it.error = false
                        break
                    }
                }
                noteAll()
            }
        }
    }

    fun setNoteText(num: Int) {
//        array[curSelectIndex]?.let {
//            it.noteNum[num - 1] = if (it.noteNum[num - 1] == 0) num else 0
//            invalidate()
//        }
    }

    fun clearAll() {
        for (unitBean in array) {
            unitBean?.let {
                it.confirmNum = 0
            }
        }
        noteAll()
    }

    //将一个矩形分割成9个小矩形
    private fun getNineGrid(rectF: RectF): Array<RectF?> {
        val rectFs = arrayOfNulls<RectF>(9)
        val height = rectF.bottom - rectF.top
        val width = rectF.right - rectF.left
        val top2 = rectF.top + height / 3
        val top3 = rectF.top + (height * 2 / 3)
        val left2 = rectF.left + width / 3
        val left3 = rectF.left + (width * 2 / 3)
        for (i in rectFs.indices) {
            val smallRectF = RectF()
            when (i) {
                0 -> {
                    smallRectF.top = rectF.top
                    smallRectF.bottom = top2
                    smallRectF.left = rectF.left
                    smallRectF.right = left2
                }
                1 -> {
                    smallRectF.top = rectF.top
                    smallRectF.bottom = top2
                    smallRectF.left = left2
                    smallRectF.right = left3
                }
                2 -> {
                    smallRectF.top = rectF.top
                    smallRectF.bottom = top2
                    smallRectF.left = left3
                    smallRectF.right = rectF.right
                }
                3 -> {
                    smallRectF.top = top2
                    smallRectF.bottom = top3
                    smallRectF.left = rectF.left
                    smallRectF.right = left2
                }
                4 -> {
                    smallRectF.top = top2
                    smallRectF.bottom = top3
                    smallRectF.left = left2
                    smallRectF.right = left3
                }
                5 -> {
                    smallRectF.top = top2
                    smallRectF.bottom = top3
                    smallRectF.left = left3
                    smallRectF.right = rectF.right
                }
                6 -> {
                    smallRectF.top = top3
                    smallRectF.bottom = rectF.bottom
                    smallRectF.left = rectF.left
                    smallRectF.right = left2
                }
                7 -> {
                    smallRectF.top = top3
                    smallRectF.bottom = rectF.bottom
                    smallRectF.left = left2
                    smallRectF.right = left3
                }
                8 -> {
                    smallRectF.top = top3
                    smallRectF.bottom = rectF.bottom
                    smallRectF.left = left3
                    smallRectF.right = rectF.right
                }

            }
            rectFs[i] = smallRectF
        }
        return rectFs
    }

    //计算的总方法入口
    fun calculation() {
        var change = true
        var changeZ = false
        var changeX: Boolean
        var changeY = false
//        for (i in 1..9) {
        val i = curSelectUnitBean?.confirmNum ?: 0
        if (i == 0) return
        changeX = singleTroubleshoot(i, arrayX)
        if (!changeX) {
            changeY = singleTroubleshoot(i, arrayY)
            if (!changeY) {
                changeZ = singleTroubleshoot(i, arrayZ)
            }
        }
//            change = changeX || changeY || changeZ
//        }
        noteAll()
        invalidate()
    }

    //Troubleshoot  单个数字排查
    private fun singleTroubleshoot(num: Int, array: Array<Array<UnitBean?>?>): Boolean {

        var change = false
        var changeUnitBean: UnitBean? = null
        for (arrays in array) {
            if (arrays != null) {
                var time = 0
                if (time > 1) continue
                for (unitBean in arrays) {
                    if (unitBean != null && !unitBean.isRealConfirm()) {

                        var time0 = 0
                        var not0 = 0
                        for (i in unitBean.noteNum) {
                            if (i == num) {
                                changeUnitBean = unitBean
                                time++
                            }
                            if (i == 0) time0++ else not0 = i
                        }
                        if (time0 == unitBean.noteNum.size - 1) {
                            unitBean.confirmNum = not0
                            unitBean.note0()
                            return true
                        }
//                        if (time > 1) {
//                            changeUnitBean = null
//                            continue
//                        } else {
//                            changeUnitBean = if (time == 1) unitBean else null
//                        }
                    }
                }
                if (time == 1 && changeUnitBean != null) {
                    changeUnitBean.confirmNum = num
                    changeUnitBean.note0()
                    return true
                }
//                if (changeUnitBean != null) {
//                    changeUnitBean.confirmNum = num
//                    changeUnitBean.note0()
//                    return true
//                }
            }
        }
        return change
    }

    fun updateData(data: ArrayList<UnitBean?>?) {
        data?.let {
            for (i in data.indices) {
                if (i in data.indices) {
                    data[i]?.let { it1 -> array[i]?.copy(it1) }
                }
            }
            noteAll()
        }
    }
}