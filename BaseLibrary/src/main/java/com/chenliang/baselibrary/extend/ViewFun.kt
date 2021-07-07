package com.chenliang.baselibrary.extend

import android.view.View

fun View.show(show: Boolean) = this.apply {
    if (show) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun View.click(func: (view: View) -> Unit) = this.apply {
    this.setOnClickListener { func(it) }
}