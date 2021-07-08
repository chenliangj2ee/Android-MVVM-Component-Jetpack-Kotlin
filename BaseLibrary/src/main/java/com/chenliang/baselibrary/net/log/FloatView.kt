package com.chenliang.baselibrary.net.log

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.utils.toast

@SuppressLint("AppCompatCustomView")
class FloatView(context: Context?) : Button(context) {
    var show = false;
    var add = false
    var logs = ArrayList<BaseBeanLog>()

    init {
        setBackgroundResource(R.drawable.base_log_float_button)
        text = "GO"
        Handler().postDelayed(Runnable { show(0f, 0f) }, 500)
    }

    fun show(x: Float, y: Float) {
        if (!add) {
            add = true
            show = false
            this.x = x - 400
            this.y = y - 600
            (context as Activity).addContentView(this, ViewGroup.LayoutParams(200, 200))
            visibility = View.GONE
            return
        }

        visibility = if (show) {
            show = !show
            View.GONE
        } else {
            show = !show
            this.setOnClickListener {
                if (logs.isEmpty()) {
                    toast("不存在网络请求")
                    visibility = View.GONE
                    show = false
                    return@setOnClickListener
                }
                var dialog = LogDialog()
                dialog.setData(logs)
                visibility = View.GONE
                show = false
                dialog.show((context as FragmentActivity).supportFragmentManager, "")
            }
            View.VISIBLE

        }
        this.x = x - 100
        this.y = y - 300
    }


    fun addLog(log: BaseBeanLog) {
        logs.add(log)
    }

}

