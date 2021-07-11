package com.chenliang.baselibrary.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.show
import kotlinx.android.synthetic.main.base_layout_toolbar.view.*

class MyToolBar : LinearLayout {
    lateinit var root: View

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
        root = LayoutInflater.from(context).inflate(R.layout.base_layout_toolbar, this, true)
        root.toolbar_back.setOnClickListener {
            (context as MyBaseActivity<*,*>).onBackPressed()
        }
    }

    public fun showLeft(show: Boolean) {
        root.toolbar_back.show(show)
    }

    public fun showRight(show: Boolean) {
        root.toolbar_right.show(show)
    }

    public fun setTitle(title: String) {
        root.title.text = title
    }
}