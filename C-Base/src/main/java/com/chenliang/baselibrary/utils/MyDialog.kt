package com.chenliang.baselibrary.utils

import android.view.View
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.databinding.BaseDialogDefaultLayoutBinding
import com.chenliang.baselibrary.base.MyBaseDialog
import com.chenliang.baselibrary.annotation.MyClass
import kotlinx.android.synthetic.main.base_dialog_default_layout.view.*

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.mvvmc.demo
 * @author: chenliang
 * @date: 2021/07/14
 */

@MyClass(mDialogAnimation = true, mDialogAnimationTime = 100)
class MyDialog : MyBaseDialog<BaseDialogDefaultLayoutBinding>() {


    private var y: (() -> Unit?)? = null
    private var n: (() -> Unit?)? = null
    private var title = ""
    private var message = ""
    private var cancelText = "取消"
    private var confirmText = "确定"
    override fun initCreate() {
        mRootView?.message?.text = message
        if ("" != title) {
            mRootView?.title?.text = title;
            mRootView?.title?.show(true)
        }
        mRootView?.cancel?.text = cancelText
        mRootView?.confirm?.text = confirmText


        if (single) {
            mRootView?.cancel?.visibility = View.GONE
            mRootView?.line?.visibility = View.GONE
            mRootView?.confirm?.setBackgroundResource(R.drawable.base_selector_yes_single_bg)
        }

        mRootView?.cancel?.click {
            if (n != null) {
                n?.invoke()
            }
            dismiss()
        }
        mRootView?.confirm?.click {
            if (y != null) {
                y?.invoke()
            }
            dismiss()
        }
    }

    var single = false
    fun single(single: Boolean): MyDialog {
        this.single = single
        return this
    }

    fun title(title: String): MyDialog {
        this.title = title
        mRootView?.title?.text = title
        return this
    }

    fun message(message: String): MyDialog {
        this.message = message
        mRootView?.message?.text = message
        return this
    }

    fun y(y: () -> Unit): MyDialog {
        this.y = y
        return this
    }

    fun n(n: () -> Unit): MyDialog {
        this.n = n
        return this
    }

    fun y(text: String, y: () -> Unit): MyDialog {
        this.confirmText = text
        this.y = y
        return this
    }

    fun n(text: String, n: () -> Unit): MyDialog {
        this.cancelText = text
        this.n = n
        return this
    }
}