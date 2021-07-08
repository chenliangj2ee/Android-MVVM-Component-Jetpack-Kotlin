package com.chenliang.baselibrary.utils

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chenliang.baselibrary.BaseInit
import com.google.gson.Gson

fun View.show(show: Boolean) = this.apply {
    if (show) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun Any.toast(message: String) {
    Toast.makeText(BaseInit.con, message, Toast.LENGTH_LONG).show()
}

fun Any.log(message: String) {
    Log.i("MyLog", message)
    Log.i(this::class.java.simpleName, message)
}

fun Any.log(tag: String, message: String) {
    Log.i(tag, message)
    log(message)
}

fun Any.logE(message: String) {
    Log.e("MyLog", message)
    Log.e(this::class.java.simpleName, message)
}

fun Any.logE(tag: String, message: String) {
    Log.e(tag, message)
    logE(message)
}

fun Any.toJson(): String = Gson().toJson(this)


fun View.click(func: (view: View) -> Unit) = this.apply {
    this.setOnClickListener { func(it) }
}


fun <T : ViewModel> AppCompatActivity.initVM(modelClass: Class<T>) =
    ViewModelProvider(this)[modelClass]

fun <T> Activity.toAct(cls: Class<T>) {
    startActivity(Intent(this, cls))
}

fun Any.hasNull(vararg args: String): Boolean {
    val array = arrayOf(args)
    var size = args.size - 1

    for (index in 0..size step 2) {
        var item = args[index]
        var message = args[index + 1]

        if (item == null) {
            toast(message)
            return true
        }

        when (item) {
            is String -> {
                if (item.isEmpty()) {
                    toast(message)
                    return true
                }
            }
        }
    }
    return false
}


fun <T : ViewModel> Fragment.initVM(modelClass: Class<T>) = ViewModelProvider(this)[modelClass]