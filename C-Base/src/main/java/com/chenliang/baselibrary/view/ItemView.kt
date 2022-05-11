package com.chenliang.baselibrary.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.utils.show
import kotlinx.android.synthetic.main.base_layout_item.view.*

/**
 * author:chenliang
 * date:2021/11/8
 */
class ItemView : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.ItemView)
        var icon = typedArray?.getResourceId(R.styleable.ItemView_my_icon, -1)
        var text = typedArray?.getString(R.styleable.ItemView_my_title)
        var lineShow = typedArray?.getBoolean(R.styleable.ItemView_my_lineShow, true)
        var rightShow = typedArray?.getBoolean(R.styleable.ItemView_my_rightShow, true)

        var params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        var item = View.inflate(context!!, R.layout.base_layout_item, null)
        if (icon != null) {
            if (icon > 0) {
                item.icon.setImageResource(icon)
                item.icon.visibility = View.VISIBLE
            }
        }
        if (text != null) {
            item.title.text = text
        }
        lineShow?.let { item.line.show(it) }
        rightShow?.let { item.right_icon.show(it) }

        addView(item, params)
    }
}