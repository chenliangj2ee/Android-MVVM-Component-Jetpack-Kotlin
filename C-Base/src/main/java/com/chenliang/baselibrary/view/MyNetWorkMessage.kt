package com.chenliang.baselibrary.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.utils.MyNetWorkUtils
import com.chenliang.baselibrary.utils.hasNetWork
import com.chenliang.baselibrary.utils.networkChange
import com.chenliang.baselibrary.utils.show

class MyNetWorkMessage : LinearLayout {
    lateinit var root: View

    var boo = false;

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initViews()
    }

    fun showNetworkError(boo: Boolean) {
        this.boo = boo
        if (boo) {
            show(!hasNetWork())
            networkChange { show(!it) }
        } else {
            show(false)
        }
    }

    fun enable() {
        networkChange { show(!it && !MyNetWorkUtils.isConnected()) }
    }


    private fun initViews() {
        root =
            LayoutInflater.from(context).inflate(R.layout.base_layout_network_message, this, true)

    }


}