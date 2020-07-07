package com.yk.demo.wan.sudoku.entity

import android.graphics.RectF

class UnitBean {
    var confirmNum: Int = 0  //确定数据
    val noteNum = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9) //笔记数据
    var selected: Boolean = false
    var linked: Boolean = false
    var index = 0
    var error: Boolean = false

    //该单元所处位置
    var x = 0
    var y = 0
    var z = 0
    var rectF = RectF(0F, 0F, 0F, 0F)

    fun isConfirm() = confirmNum != 0

    fun isRealConfirm() = confirmNum != 0 && !error

    fun getRectSplitX(split: Float): Float = (y - 1).toFloat() * split

    fun getRectSplitY(split: Float): Float = (x - 1).toFloat() * split

    fun isLinked(unitBean: UnitBean): Boolean =
        unitBean.x == x || unitBean.y == y || unitBean.z == z

    fun isLinkedNotContains(unitBean: UnitBean): Boolean =
        unitBean.index != index && (unitBean.x == x || unitBean.y == y || unitBean.z == z)

    fun note0() {
        for (i in noteNum.indices) {
            noteNum[i] = 0
        }
    }

    fun copy(unitBean: UnitBean) {
        confirmNum = unitBean.confirmNum
        selected = unitBean.selected
        linked = unitBean.linked
        index = unitBean.index
        error = unitBean.error
        x = unitBean.x
        y = unitBean.y
        z = unitBean.z
        rectF.left = unitBean.rectF.left
        rectF.top = unitBean.rectF.top
        rectF.right = unitBean.rectF.right
        rectF.bottom = unitBean.rectF.bottom
        for (i in noteNum.indices) {
            noteNum[i] = unitBean.noteNum[i]
        }
    }
}