package com.chenliang.baselibrary.utils

import android.view.Gravity
import com.chenliang.baselibrary.annotation.MyClass
import com.chenliang.baselibrary.base.MyBaseDialog
import com.chenliang.baselibrary.bean.BeanMenu
import com.chenliang.baselibrary.databinding.BaseMenuDialogBinding
import com.chenliang.baselibrary.databinding.ItemBaseMenuDialogBinding
import kotlinx.android.synthetic.main.base_menu_dialog.view.*

/**
 * author:chenliang
 * date:2021/11/22
 */
@MyClass(mDialogGravity = Gravity.BOTTOM,mDialogTransparent = true,mDialogAnimation = true)
class MyMenuDialog : MyBaseDialog<BaseMenuDialogBinding>() {
    var arrayList = arrayListOf<BeanMenu>()
    lateinit var clickFun: (index: Int) -> Unit
    override fun initCreate() {
        mRootView?.recyclerView?.setEnableLoadMore(false)
        mRootView?.recyclerView?.setEnableRefresh(false)
        mRootView?.recyclerView?.bindData<BeanMenu> {
            var bean = it
            with(it.binding as ItemBaseMenuDialogBinding) {
                data = it
                itemView.click {
                    if (clickFun != null) {
                        clickFun(arrayList.indexOf(bean))
                    }
                    dismiss()
                }
            }
        }

        mRootView?.recyclerView?.addDatas(arrayList)
        mRootView?.cancel?.click { dismiss() }

    }

    fun itemClick(func: (index: Int) -> Unit) {
        this.clickFun = func
    }

    fun addMenu(resource: Int, title: String) {
        var menu = BeanMenu()
        menu.icon = resource
        menu.title = title
        arrayList.add(menu)
    }


}