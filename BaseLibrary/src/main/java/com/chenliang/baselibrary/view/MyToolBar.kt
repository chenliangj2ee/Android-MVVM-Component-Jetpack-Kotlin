package com.chenliang.baselibrary.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout

class MyToolBar : LinearLayout {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initViews()
    }

    private fun initViews() {
        var view: View =
            LayoutInflater.from(context).inflate(R.layout.base_layout_boobar, this, true)
    }
}