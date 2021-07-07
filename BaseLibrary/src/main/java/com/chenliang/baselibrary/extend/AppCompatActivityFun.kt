package com.chenliang.baselibrary.extend

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


fun <T : ViewModel> AppCompatActivity.initVM(modelClass: Class<T>) =
    ViewModelProvider(this)[modelClass]

fun <T> Activity.toAct(cls: Class<T>) {
    startActivity(Intent(this, cls))
}
