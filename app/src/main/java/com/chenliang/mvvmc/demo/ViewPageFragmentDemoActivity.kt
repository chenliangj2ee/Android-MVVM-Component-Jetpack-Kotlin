package com.chenliang.mvvmc.demo

import androidx.fragment.app.Fragment
import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.goto
import com.chenliang.mvvmc.databinding.ActivityViewpagerFragmentBinding
import kotlinx.android.synthetic.main.activity_viewpager_fragment.*

@My(myToolbarTitle = "ViewPager Tab")
class ViewPageFragmentDemoActivity :
    MyBaseActivity<ActivityViewpagerFragmentBinding, DefaultViewModel>() {


    override fun initCreate() {
        viewpager.setTabLayout(tabLayout, "tab1", "tab2", "tab3")
        viewpager.addFragments(goto(FragmentNoToolbarRefreshDemo::class.java,"type","1") as Fragment)
        viewpager.addFragments(goto(FragmentNoToolbarRefreshDemo::class.java,"type","2") as Fragment)
        viewpager.addFragments(goto(FragmentNoToolbarRefreshDemo::class.java,"type","3") as Fragment)
    }


}