package com.chenliang.baselibrary.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.OverScroller
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import java.lang.Exception
import java.lang.reflect.Field

/**
 * author:chenliang
 * date:2022/4/1
 */
class FixAppBarLayout : AppBarLayout.Behavior {
    constructor() : super() {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        ev: MotionEvent
    ): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val scroller = getSuperSuperField(this, "mScroller")
            if (scroller != null && scroller is OverScroller) {
                scroller.abortAnimation()
            }
        }
        return super.onInterceptTouchEvent(parent, child, ev)
    }

    private fun getSuperSuperField(paramClass: Any, paramString: String): Any? {
        var field: Field? = null
        var `object`: Any? = null
        try {
            field = paramClass.javaClass.superclass.superclass.getDeclaredField(paramString)
            field.isAccessible = true
            `object` = field[paramClass]
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return `object`
    }

    companion object {
        private const val TAG = "AppBarLayoutBehavior"
    }
}