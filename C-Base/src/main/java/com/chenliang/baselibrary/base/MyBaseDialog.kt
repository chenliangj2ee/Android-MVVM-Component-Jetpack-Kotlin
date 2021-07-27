package com.chenliang.baselibrary.base

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.annotation.dialogGravity
import com.chenliang.baselibrary.annotation.dialogTransparent
import com.chenliang.baselibrary.annotation.myDialogAnimation
import com.chenliang.baselibrary.annotation.myDialogAnimationTime
import com.chenliang.baselibrary.utils.MyKotlinClass
import com.chenliang.baselibrary.utils.dip2px

abstract class MyBaseDialog<Binding : ViewDataBinding> : DialogFragment() {
    lateinit var mBinding: Binding
    lateinit var mRootView: LinearLayout

    override fun onStart() {
        super.onStart()
        val dm = android.util.DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(dm)
        dialog?.window?.setLayout(
            dm.widthPixels - 60.dip2px(),
            android.view.ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setGravity(dialogGravity(this))
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        dialog?.window?.requestFeature(android.view.Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): android.view.View? {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        mRootView = layoutInflater.inflate(R.layout.base_dialog_content, null) as LinearLayout

        if (!dialogTransparent(this)) {
            mRootView.setBackgroundResource(R.drawable.base_selector_dialog_bg)
        }

        bindView()
        initCreate()
        if (myDialogAnimation(this)) {
            if (dialogGravity(this) == Gravity.BOTTOM) {
                initAnimationTranslation(mRootView)
            } else {
                initAnimationScaleX(mRootView)
            }
        }


        return mRootView
    }

    /**
     * 缩放、透明度动画
     */
    private fun initAnimationScaleX(view: View) {
        var scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.5F, 1F)
        var scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.5F, 1F)
        var alpha = ObjectAnimator.ofFloat(view, "alpha", 0.5F, 1F)
        var scaleSet = AnimatorSet()
        scaleSet.duration =  myDialogAnimationTime(this)
        scaleSet.playTogether(scaleX, scaleY, alpha)
        scaleSet.interpolator = AccelerateDecelerateInterpolator()
        scaleSet.start()
    }
    /**
     * 从下到上移动动画
     */
    private fun initAnimationTranslation(view: View) {
        var translationY = ObjectAnimator.ofFloat(view, "translationY", 300F, 0F)
        translationY.interpolator = AccelerateDecelerateInterpolator()
        translationY.duration = myDialogAnimationTime(this)
        translationY.start()
    }

    private fun bindView() {
        var content = layoutInflater.inflate(layoutId(), null)
        mBinding = DataBindingUtil.bind<Binding>(content)!!
        mRootView.addView(
            content,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )

    }

    abstract fun initCreate()
    private fun layoutId(): Int {
        return MyKotlinClass.getLayoutIdByBinding(
            requireContext(),
            this::class.java.genericSuperclass.toString().split("<")[1].split(",")[0].replace(
                ">",
                ""
            )
        )
    }

    fun show(con: AppCompatActivity) {

        if (con.isDestroyed || isAdded)
            return
        try {
            this.show(con.supportFragmentManager, "")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun show(con: AppCompatActivity, tag: String) {
        if (con.isDestroyed || isAdded)
            return
        try {
            var frag = con.supportFragmentManager.findFragmentByTag(tag)
            if (frag != null)
                return
            this.show(con.supportFragmentManager, tag)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}