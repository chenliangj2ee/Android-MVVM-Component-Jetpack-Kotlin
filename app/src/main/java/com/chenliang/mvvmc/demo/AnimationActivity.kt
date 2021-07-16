package com.chenliang.mvvmc.demo

import android.animation.ObjectAnimator
import android.view.MotionEvent
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.click
import com.chenliang.baselibrary.utils.log
import com.chenliang.mvvmc.databinding.ActivityAnimationBinding
import kotlinx.android.synthetic.main.activity_animation.*


/**
 * 未完结
 * @property eventX Int
 * @property eventY Int
 */
class AnimationActivity : MyBaseActivity<ActivityAnimationBinding, DefaultViewModel>() {


    override fun initCreate() {

        button1.click {
            button1.start(mW, mH)
        }
        button2.click {
            button2.start(mW, mH)
        }
    }


    var eventX = 0
    var eventY = 0

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event!!.action == MotionEvent.ACTION_UP) {
            eventX = event!!.x.toInt()
            eventY = event!!.y.toInt()
            if(button1.boo){
                button1.x = eventX.toFloat()
                button1.y = eventY.toFloat()
            }
            button1.initWH2()
            button1.performClick()
            log("button click onTouchEvent action up ....")
        }
        return super.onTouchEvent(event)

    }


}