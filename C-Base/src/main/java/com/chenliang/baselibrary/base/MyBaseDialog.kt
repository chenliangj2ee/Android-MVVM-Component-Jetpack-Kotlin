package com.chenliang.baselibrary.base

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AppCompatActivity
import com.chenliang.baselibrary.utils.dip2px

abstract class MyBaseDialog<Binding : ViewDataBinding> : DialogFragment() {
    lateinit var binding: Binding
    private lateinit var layout: View

    override fun onStart() {
        super.onStart()
        val dm = android.util.DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(dm)
        dialog?.window?.setLayout(  dm.widthPixels - 60.dip2px(),
            android.view.ViewGroup.LayoutParams.WRAP_CONTENT
        )
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
        binding = DataBindingUtil.inflate(inflater, layoutId(), container, false);
        layout = binding.root
        initCreate()
        return layout
    }

    abstract fun initCreate()
    abstract fun layoutId(): Int
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