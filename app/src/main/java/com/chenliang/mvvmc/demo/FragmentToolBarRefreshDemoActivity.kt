package com.chenliang.mvvmc.demo

import androidx.fragment.app.Fragment
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.goto
import com.chenliang.mvvmc.R
import com.chenliang.mvvmc.databinding.ActivityFragmentNoToolbarBinding

class FragmentToolBarRefreshDemoActivity : MyBaseActivity<ActivityFragmentNoToolbarBinding, DefaultViewModel>() {
    override fun initCreate() {

        replace(R.id.fragment,goto(FragmentToolbarRefreshDemo::class.java) as Fragment)
    }


}