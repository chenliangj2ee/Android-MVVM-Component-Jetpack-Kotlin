package com.chenliang.account.bean

import androidx.databinding.Bindable
import com.chenliang.baselibrary.base.MyBaseBean

class BeanUser : MyBaseBean() {
    @Bindable
    var name = ""
    var password = ""
    var age = 0
}