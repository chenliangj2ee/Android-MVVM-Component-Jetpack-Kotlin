package com.chenliang.mvvmc.demo

import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.mvvmc.databinding.ActivityViewpagerFragmentBinding
import kotlinx.android.synthetic.main.activity_viewpager_fragment.*

@My(myToolbarTitle = "ViewPager Tab")
class ViewPageFragmentDemoActivity :
    MyBaseActivity<ActivityViewpagerFragmentBinding, DefaultViewModel>() {


    override fun initCreate() {
        viewpager.setTabLayout(tabLayout, "tab1", "tab2", "tab3")
        viewpager.addFragments(FragmentNoToolbarRefreshDemo())
        viewpager.addFragments(FragmentNoToolbarRefreshDemo())
        viewpager.addFragments(FragmentNoToolbarRefreshDemo())
    }


}