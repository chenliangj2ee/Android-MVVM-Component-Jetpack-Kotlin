package com.chenliang.mvvmc.demo

import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.goto
import com.chenliang.mvvmc.R
import com.chenliang.mvvmc.databinding.ActivityLibraryFragmentBinding

class LibraryFragmentActivity : MyBaseActivity<ActivityLibraryFragmentBinding, DefaultViewModel>() {
    override fun initCreate() {
        replace(R.id.content, goto("/account/my", "value", "我来自App传参"))
    }


}