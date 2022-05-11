package com.chenliang.baselibrary.view

import android.view.Gravity
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter
import com.chenliang.baselibrary.annotation.MyClass
import com.chenliang.baselibrary.base.MyBaseDialog
import com.chenliang.baselibrary.databinding.BaseDialogPickerBinding
import com.chenliang.baselibrary.utils.click


/*      使用：
        var items=arrayListOf<String>("item1", "item2", "item3", "item4")
        var dialog = DialogPicker()
        dialog.setItems(items)
        dialog.setTitle("学历")
        dialog.selected {posi->

        }
        dialog.show(this)
 */
/**
 * author:chenliang
 * date:2021/11/11
 */
@MyClass(mDialogGravity = Gravity.BOTTOM, mDialogAnimation = true)
class DialogPicker : MyBaseDialog<BaseDialogPickerBinding>() {
    private var func: ((index: Int) -> Unit)? = null
    private var datas = ArrayList<String>()
    var titleText = ""
    override fun initCreate() {
        mBinding?.wheel?.setCyclic(false)
        mBinding?.no?.click { dismiss() }
        mBinding?.title?.text = titleText
        mBinding?.yes?.click {
            func?.let { it1 -> it1(mBinding?.wheel!!.currentItem) }
            dismiss()
        }
        mBinding?.wheel?.adapter = ArrayWheelAdapter<Any?>(datas as List<Any?>?)
    }

    fun setTitle(title: String) {
        this.titleText = title;
    }

    fun setItems(items: ArrayList<String>) {
        this.datas = items
    }

    fun selected(func: (index: Int) -> Unit) {
        this.func = func
    }
}