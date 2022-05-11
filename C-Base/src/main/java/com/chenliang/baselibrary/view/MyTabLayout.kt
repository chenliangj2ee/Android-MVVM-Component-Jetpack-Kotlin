package com.chenliang.baselibrary.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.utils.selected
import com.chenliang.baselibrary.utils.setBold
import com.chenliang.baselibrary.utils.setTextSizeDip
import com.google.android.material.tabs.TabLayout

/**
 * author:chenliang
 * date:2022/4/6
 */
class MyTabLayout(context: Context, attrs: AttributeSet?) : TabLayout(context, attrs) {

    var textSizeDefault = 14
    var textSizeSelected = 14

    var textColorDefault = "515357"
    var textColorSelected = "515357"

    var bold = false

    init {
        selected {
            with(it?.customView?.findViewById<TextView>(R.id.title)) {
                this?.isEnabled = it?.isSelected == false
                this?.setTextSizeDip(if (it?.isSelected == true) textSizeSelected else textSizeDefault)
                if (bold)
                    this?.setBold(it?.isSelected == true)
                this?.setTextColor(android.graphics.Color.parseColor(if (it?.isSelected == true) textColorSelected else textColorDefault))
            }
        }
    }
}