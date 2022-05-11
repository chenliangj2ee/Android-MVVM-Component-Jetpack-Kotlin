package com.chenliang.baselibrary.annotation

import java.lang.reflect.Type

class MyRetrofitGoValue(
    val loading: Boolean,
    val cache: Boolean,
    val hasCacheLoading: Boolean,
    val tag: String,
    val failToast: Boolean,
    val successCode: Int,
    val failCode: Int,
    val type: Type?=null,
    var mDataIsNullToError: Boolean,
)