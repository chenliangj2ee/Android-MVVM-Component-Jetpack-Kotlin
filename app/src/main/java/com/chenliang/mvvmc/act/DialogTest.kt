package com.chenliang.mvvmc.act

import com.chenliang.baselibrary.base.MyBaseDialog
import com.chenliang.mvvmc.R
import com.chenliang.mvvmc.databinding.DialogLoginBinding

class DialogTest : MyBaseDialog<DialogLoginBinding>() {
    override fun layoutId(): Int {
        return R.layout.dialog_login
    }

    override fun initCreate() {
        binding.message = "dd"
    }
}