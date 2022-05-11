package com.chenliang.baselibrary.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DividerItemLine(
    context: Context?,
    private val dividerHeight: Float,
    color: Int,
    marginLeft: Int,
    marginRight: Int
) :
    RecyclerView.ItemDecoration() {
    var marginLeft = marginLeft
    var marginRight = marginRight
    var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var color = color ?: Color.parseColor("#E6F0F2")
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = dividerHeight.toInt()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        mPaint.color = this.color
        val childCount = parent.childCount
        val left = parent.paddingLeft + marginLeft
        val right = parent.width - parent.paddingRight - marginRight
        for (i in 0 until childCount - 1) {
            val view = parent.getChildAt(i)
            val top = view.bottom.toFloat()
            val bottom = (view.bottom + dividerHeight).toFloat()
            c.drawRect(left.toFloat(), top, right.toFloat(), bottom, mPaint)
        }
    }

}