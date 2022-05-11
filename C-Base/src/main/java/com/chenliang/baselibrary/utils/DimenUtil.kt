package com.chenliang.baselibrary.utils

import android.content.Context
import android.util.TypedValue

object DimenUtil {

    fun dp2Px(context: Context, dpValue: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue.toFloat(), context.resources.displayMetrics).toInt()
    }


}