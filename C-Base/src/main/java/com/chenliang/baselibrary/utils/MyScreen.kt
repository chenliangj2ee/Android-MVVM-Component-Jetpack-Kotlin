package com.chenliang.baselibrary.utils

import android.R
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewConfiguration
import android.view.Window
import android.view.WindowManager
import com.chenliang.baselibrary.BaseInit

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.baselibrary.utils
 * @author: chenliang
 * @date: 2021/07/09
 */
object MyScreen {

    /**
     * tag screen：获取屏幕宽
     * 获得屏幕宽度
     * @return Int
     */
    fun getScreenWidth(): Int {
        val metric = DisplayMetrics()
        var wm = BaseInit.con!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metric)
        return metric.widthPixels
    }


    /**
     * tag screen：获得屏幕高度
     * 获得屏幕高度
     * @return Int
     */
    fun getScreenHeight(): Int {
        val metric = DisplayMetrics()
        var wm = BaseInit.con!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metric)
        return metric.heightPixels
    }

    /**
     * tag screen：获得状态栏高度
     * 获得状态栏高度
     * @return Int
     */
    fun getStatusHeight(): Int {
        var result = 0
        val resourceId: Int = BaseInit.con!!.resources
            .getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = BaseInit.con!!.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    /**
     * tag screen：判断导航栏是否显示
     * 判断导航栏是否显示
     * @param context Context
     * @param window Window
     * @return Boolean
     */
    fun isNavigationBarShow(context: Context, window: Window): Boolean {
        val show: Boolean
        val display = window.windowManager.defaultDisplay
        val point = Point()
        display.getRealSize(point)
        val decorView = window.decorView
        val conf = context.resources.configuration
        show = if (Configuration.ORIENTATION_LANDSCAPE == conf.orientation) {
            val contentView = decorView.findViewById<View>(R.id.content)
            point.x != contentView.width
        } else {
            val rect = Rect()
            decorView.getWindowVisibleDisplayFrame(rect)
            rect.bottom != point.y
        }
        return show
    }

    /**
     * tag screen：获取导航栏高度
     * 获取导航栏高度
     * @return Int
     */
    fun getNavigationBarHeight(): Int {
        val resources: Resources = BaseInit.con!!.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }



    /**
     * tag screen：获取屏幕宽度【包含状态栏，导航栏】
     * 获取屏幕宽度【包含状态栏，导航栏】
     * @return Int
     */
    private fun getScreenWidthAll(): Int {
        var dpi = 0
        val windowManager = BaseInit.con!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        val c: Class<*>
        try {
            c = Class.forName("android.view.Display")
            val method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
            method.invoke(display, displayMetrics)
            dpi = displayMetrics.widthPixels
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dpi
    }

    /**
     * tag screen：获取屏幕高度【包含状态栏，导航栏】
     * 获取屏幕宽度【包含状态栏，导航栏】
     * @return Int
     */
    private fun getScreenHeightAll(): Int {
        var dpi = 0
        val windowManager = BaseInit.con!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        val c: Class<*>
        try {
            c = Class.forName("android.view.Display")
            val method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
            method.invoke(display, displayMetrics)
            dpi = displayMetrics.heightPixels
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dpi
    }


    /**
     * tag screen：设置Activity是否全屏
     * 设置Activity是否全屏
     * @param activity Activity?
     * @param isShowStatusBar Boolean
     * @param isShowNavigationBar Boolean
     */
    fun setFullscreen(activity: Activity?, isShowStatusBar: Boolean, isShowNavigationBar: Boolean) {
        if (activity != null && activity.window != null) {
            setFullscreen(activity.window, isShowStatusBar, isShowNavigationBar)
        }
    }

    private fun setFullscreen(window: Window?, isShowStatusBar: Boolean, isShowNavigationBar: Boolean) {
        var uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        if (!isShowStatusBar) {
            uiOptions = uiOptions or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        if (!isShowNavigationBar) {
            uiOptions = uiOptions or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            uiOptions = uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE
        }
        if (window != null) {
            window.decorView.systemUiVisibility = uiOptions
        }
    }

    /**
     * tag screen：设置状态栏颜色
     * 设置状态栏颜色
     * @param activity Activity?
     * @param color Int
     */
    fun setStatusBarColor(activity: Activity?, color: Int) {
        if (activity != null && activity.window != null) {
            setStatusBarColor(activity.window, color)
        }
    }

    private fun setStatusBarColor(window: Window?, color: Int) {
        if (Build.VERSION.SDK_INT >= 21) {
            if (window != null) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = color
            }
        }
    }


    /**
     * tag screen：判断是否有虚拟导航栏
     * 判断是否有虚拟导航栏
     * @param context Context
     * @return Boolean
     */
    fun hasNavigationBar(context: Context): Boolean {
        val res = context.resources
        val resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android")
        return if (resourceId != 0) {
            var hasNav = res.getBoolean(resourceId)
            // check override flag
            val sNavBarOverride: String? = getNavBarOverride()
            if ("1" == sNavBarOverride) {
                hasNav = false
            } else if ("0" == sNavBarOverride) {
                hasNav = true
            }
            hasNav
        } else { // fallback
            !ViewConfiguration.get(context).hasPermanentMenuKey()
        }
    }
    private fun getNavBarOverride(): String? {
        var sNavBarOverride: String? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                val c = Class.forName("android.os.SystemProperties")
                val m = c.getDeclaredMethod("get", String::class.java)
                m.isAccessible = true
                sNavBarOverride = m.invoke(null, "qemu.hw.mainkeys") as String
            } catch (e: Throwable) {
            }
        }
        return sNavBarOverride
    }
    /**
     * tag screen：dip转px
     * dip转px
     * @param context Context
     * @param dpValue Float
     * @return Int
     */


    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * tag screen：px转dip
     * px转dip
     * @param context Context
     * @param pxValue Int
     * @return Int
     */
    fun px2dip(context: Context, pxValue: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            pxValue.toFloat(),
            context.resources.displayMetrics
        )
            .toInt()
    }


    /**
     * tag screen：sp转px
     * sp转px
     * @param context Context
     * @param spValue Float
     * @return Int
     */
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

}

