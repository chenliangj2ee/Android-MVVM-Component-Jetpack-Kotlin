package com.chenliang.baselibrary.extend

import android.view.View

fun View.show(show: Boolean) = this.apply {
    if (show) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}