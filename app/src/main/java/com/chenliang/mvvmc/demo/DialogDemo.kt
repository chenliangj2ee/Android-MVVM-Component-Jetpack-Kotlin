package com.chenliang.mvvmc.demo

import android.view.Gravity
import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.MyBaseDialog
import com.chenliang.baselibrary.utils.click
import com.chenliang.mvvmc.databinding.DialogLayoutBinding
import kotlinx.android.synthetic.main.dialog_layout.view.*

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.mvvmc.demo
 * @author: chenliang
 * @date: 2021/07/14
 */

@My(myDialogGravity = Gravity.BOTTOM,myDialogTransparent = true,myDialogAnimation = true,myDialogAnimationTime = 200)
class DialogDemo : MyBaseDialog<DialogLayoutBinding>() {
    override fun initCreate() {
        mRootView.confirm.click { dismiss() }
    }
}