package com.chenliang.baselibrary.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chenliang.baselibrary.net.BaseResponse

open class MyBaseViewModel : ViewModel() {

    var dataMap = HashMap<String, MutableLiveData<BaseResponse<Any>>>()

}