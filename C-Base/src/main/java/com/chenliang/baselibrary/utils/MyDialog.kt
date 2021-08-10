package com.chenliang.baselibrary.utils

import com.chenliang.baselibrary.annotation.MyClass
import com.chenliang.baselibrary.base.MyBaseDialog
import com.chenliang.baselibrary.databinding.BaseDialogDefaultLayoutBinding
import kotlinx.android.synthetic.main.base_dialog_default_layout.view.*

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.mvvmc.demo
 * @author: chenliang
 * @date: 2021/07/14
 */

@MyClass(mDialogAnimation = true)
class MyDialog : MyBaseDialog<BaseDialogDefaultLayoutBinding>() {


    private var y: (() -> Unit?)? = null
    private var n: (() -> Unit?)? = null
    private var message = ""
    private var cancelText = "取消"
    private var confirmText = "确定"
    override fun initCreate() {
        mRootView.message.text = message

        mRootView.cancel.text = cancelText
        mRootView.confirm.text = confirmText

        mRootView.cancel.click {
            if (n != null) {
                n?.invoke()
                dismiss()
            }
        }
        mRootView.confirm.click {
            if (y != null) {
                y?.invoke()
                dismiss()
            }
        }
    }

    fun message(message: String): MyDialog {
        this.message = message
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