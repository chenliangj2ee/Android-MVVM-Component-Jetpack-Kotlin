package com.chenliang.baselibrary.base

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AppCompatActivity
class MyBaseDialog<Binding : ViewDataBinding>(id: Int) : DialogFragment() {
    private lateinit var binding: Binding
    private var layoutId = id

    private lateinit var layout: View

    override fun onStart() {
        super.onStart()
        val dm = android.util.DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(dm)
//        dialog?.window?.setLayout(
//            (dm.widthPixels - ScreenUtils.dip2px(context, 60f)),
//            android.view.ViewGroup.LayoutParams.WRAP_CONTENT
//        )
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        dialog?.window?.requestFeature(android.view.Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): android.view.View? {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false);
        layout = binding.root
        return layout
    }


    fun show(con: AppCompatActivity) {
//
//        if (con.isDestroyed || isAdded)
//            return
//        try {
//            this.show(con.supportFragmentManager, "")
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

    }

    fun show(con: AppCompatActivity, tag: String) {
//        if (con.isDestroyed || isAdded)
//            return
//        try {
//            var frag = con.supportFragmentManager.findFragmentByTag(tag)
//            if (frag != null)
//                return
//            this.show(con.supportFragmentManager, tag)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }
}