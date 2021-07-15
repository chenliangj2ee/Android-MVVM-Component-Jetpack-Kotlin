package com.chenliang.mvvmc.demo

import android.animation.ObjectAnimator
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.click
import com.chenliang.mvvmc.databinding.ActivityAnimationBinding
import kotlinx.android.synthetic.main.activity_animation.*

class AnimationActivity : MyBaseActivity<ActivityAnimationBinding, DefaultViewModel>() {


    override fun initCreate() {

        button.click { button.start(mW,mH) }
    }




}