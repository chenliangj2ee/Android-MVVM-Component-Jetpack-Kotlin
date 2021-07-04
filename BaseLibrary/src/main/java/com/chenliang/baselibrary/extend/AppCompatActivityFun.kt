package com.chenliang.baselibrary.extend

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


fun <T : ViewModel> AppCompatActivity.initVM(modelClass: Class<T>) = ViewModelProvider(this)[modelClass]