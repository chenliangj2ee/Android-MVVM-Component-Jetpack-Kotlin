package com.chenliang.baselibrary.extend

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <T : ViewModel> Fragment.initVM(modelClass: Class<T>) = ViewModelProvider(this)[modelClass]