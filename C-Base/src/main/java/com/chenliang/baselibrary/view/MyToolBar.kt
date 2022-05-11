package com.chenliang.baselibrary.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.click
import com.chenliang.baselibrary.utils.show
import kotlinx.android.synthetic.main.base_layout_toolbar.view.*

class MyToolBar : LinearLayout {
    lateinit var root: View

    lateinit var customLayout: LinearLayout

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
        root = LayoutInflater.from(context).inflate(R.layout.base_layout_toolbar,this)
        customLayout = root.findViewById(R.id.customLayout)
        root.toolbar_back.setOnClickListener {
            (context as MyBaseActivity<*, *>).onBackPressed()
        }
//        addView(
//            root,
//            LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
//        )
    }


    fun addCustomView(view: View, width: Int, height: Int) {
        customLayout.addView(view, width, height)
    }

    public fun showLeft(show: Boolean) {
        root.toolbar_back.show(show)
    }

    public fun showRight(text: String, func: () -> Unit) {
        root.toolbar_right.show(true)
        root.toolbar_right.text = text
        root.toolbar_right.click { func() }

    }

    public fun showRightIcon(icon: Int, func: () -> Unit) {
        root.rightIcon.show(true)
        root.rightIcon.setImageResource(icon)
        root.rightIcon.click { func() }

    }

    public fun showRightIcon(icon: Int) {
        root.rightIcon.show(true)
        root.rightIcon.setImageResource(icon)

    }

    public fun setTitle(title: String) {
        root.title.text = title
        if (title.isNotEmpty())
            root.show(true)
    }
}