package com.chenliang.baselibrary.base

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.utils.MyKotlinClass
import com.chenliang.baselibrary.utils.postDelayed
import com.chenliang.baselibrary.annotation.dialogGravity
import com.chenliang.baselibrary.annotation.dialogTransparent
import com.chenliang.baselibrary.annotation.myDialogAnimation
import com.chenliang.baselibrary.annotation.myDialogAnimationTime
import com.chenliang.baselibrary.utils.dip2px
import gorden.rxbus2.RxBus

abstract class MyBaseDialog<Binding : ViewDataBinding> : DialogFragment() {
    lateinit var mBinding: Binding
    var mRootView: LinearLayout? = null
    var dismissFun: (() -> Unit?)? = null
    var mCancelable = true
    var w = 0
    var h = 0
    override fun onStart() {
        super.onStart()
        RxBus.get().register(this)
        val dm = android.util.DisplayMetrics()
        w = dm.widthPixels
        h = dm.heightPixels
        activity?.windowManager?.defaultDisplay?.getMetrics(dm)

        var gravity = dialogGravity(this)
        if (Gravity.BOTTOM == gravity) {
            dialog?.window?.setLayout(
                dm.widthPixels,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT
            )
        } else {
            dialog?.window?.setLayout(
                dm.widthPixels - 100.dip2px(),
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog?.window?.setLayout(
                dm.widthPixels - 100.dip2px(),
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        dialog?.window?.setGravity(gravity)
        dialog?.setOnDismissListener {
            if (dismissFun != null) {
                dismissFun?.let { it1 -> it1() }
            }
            dismiss()
        }

    }

    fun dismiss(dis: () -> Unit) {
        dismissFun = dis
    }

    override fun dismiss() {
        try {
            initAnimationTranslationFinish(mRootView!!)
            postDelayed(myDialogAnimationTime(this)) { super.dismiss() }
            RxBus.get().unRegister(this)
        } catch (e: Exception) {
        }

    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        dialog?.window?.requestFeature(android.view.Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        isCancelable = mCancelable
    }

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): android.view.View? {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        if (mRootView == null)
            mRootView = layoutInflater.inflate(R.layout.base_dialog_content, null) as LinearLayout

        if (!dialogTransparent(this)) {
            mRootView?.setBackgroundResource(R.drawable.base_selector_dialog_bg)
        }
        dialog?.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dismiss()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false

        }
        bindView()
        initCreate()
        if (myDialogAnimation(this)) {
            if (dialogGravity(this) == Gravity.BOTTOM) {
                initAnimationTranslation(mRootView!!)
            } else {
                initAnimationScaleX(mRootView!!)
            }
        }

        initClick()
        if (!mCancelable) {
            dialog?.setCanceledOnTouchOutside(false)
            dialog?.setCancelable(false)
            dialog?.setOnKeyListener { dialog, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    true
                }
                false
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
        scaleSet.duration = myDialogAnimationTime(this)
        scaleSet.playTogether(scaleX, scaleY, alpha)
        scaleSet.interpolator = AccelerateDecelerateInterpolator()
        scaleSet.start()
    }

    /**
     * 从下到上移动动画
     */
    private fun initAnimationTranslation(view: View) {
        var translationY = ObjectAnimator.ofFloat(view, "translationY", 1600F, 0F)
        translationY.interpolator = AccelerateDecelerateInterpolator()
        translationY.duration = myDialogAnimationTime(this)
        translationY.start()
    }

    /**
     * 从下到上移动动画
     */
    private fun initAnimationTranslationFinish(view: View) {
        var translationY = ObjectAnimator.ofFloat(view, "translationY", view.y, 1600F)
        translationY.interpolator = AccelerateDecelerateInterpolator()
        translationY.duration = myDialogAnimationTime(this)
        translationY.start()
    }

    private fun bindView() {
        var content = layoutInflater.inflate(layoutId(), null)
        mRootView?.removeAllViews()
        mBinding = DataBindingUtil.bind<Binding>(content)!!
        mRootView?.addView(
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

    open fun cancelable(boo: Boolean): MyBaseDialog<Binding> {
        mCancelable = boo
        return this
    }

    open fun show(con: AppCompatActivity): DialogFragment {

        if (con.isDestroyed || isAdded)
            return this
        try {
            this.show(con.supportFragmentManager, "")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }


    open fun show(fragment: Fragment) {
        var con = fragment.context as AppCompatActivity
        if (con.isDestroyed || isAdded)
            return
        try {
            this.show(con.supportFragmentManager, "")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    open fun show(con: AppCompatActivity, tag: String) {
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


    open fun initClick() {

    }

}