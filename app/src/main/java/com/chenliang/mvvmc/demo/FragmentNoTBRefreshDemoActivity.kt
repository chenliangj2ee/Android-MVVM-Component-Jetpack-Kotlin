package com.chenliang.mvvmc.demo

import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.goto
import com.chenliang.mvvmc.R
import com.chenliang.mvvmc.databinding.ActivityFragmentNoToolbarBinding

class FragmentNoTBRefreshDemoActivity : MyBaseActivity<ActivityFragmentNoToolbarBinding, DefaultViewModel>() {
    override fun initCreate() {
        replace(R.id.fragment,goto(FragmentNoToolbarRefreshDemo::class.java,"type","1"))
    }


}