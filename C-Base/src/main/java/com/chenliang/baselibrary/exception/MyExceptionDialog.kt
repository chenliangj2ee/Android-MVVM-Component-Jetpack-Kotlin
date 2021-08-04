package com.chenliang.baselibrary.exception

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.chenliang.baselibrary.R

/**
 * Created by chenliangj2ee on 2016/6/19.
 */
/**
 * 使用实例：
 * AsExceptionDialog dialog = new AsExceptionDialog(this);
 * dialog.setTitle("提示").setMessage("确定要退出登录吗？").setCanceledOnTouchOutside(false);
 * dialog.setYesListener("确定", new DialogInterface.OnClickListener() {
 * public void onClick(DialogInterface dialog, int which) {
 * dialog.dismiss();
 * //退出登录
 * }
 * });
 * dialog.show();
 */
class MyExceptionDialog(context: Context) {
    var layout: View
    private val context: Context? = null
    private val title: TextView
    private val message: TextView
    private val yes: Button
    private val no: Button
    private val dialog: Dialog
    private var positiveButtonClickListener: DialogInterface.OnClickListener? = null
    private var negativeButtonClickListener: DialogInterface.OnClickListener? = null
    fun show() {
        dialog.show()
    }

    fun setCanceledOnTouchOutside(boo: Boolean): MyExceptionDialog {
        dialog.setCanceledOnTouchOutside(boo)
        return this
    }

    fun setMessage(message: String?): MyExceptionDialog {
        this.message.text = message
        return this
    }

    val messageBitmap: Bitmap
        get() {
            val w = layout.width
            val h = layout.height
            val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            val c = Canvas(bmp)
            c.drawColor(Color.WHITE)
            layout.layout(0, 0, w, h)
            layout.draw(c)
            return bmp
        }

    fun setMessage(message: Int): MyExceptionDialog {
        this.message.setText(message)
        return this
    }

    fun setTitle(title: Int): MyExceptionDialog {
        this.title.setText(title)
        return this
    }

    fun dismiss(): MyExceptionDialog {
        dialog.dismiss()
        return this
    }

    fun setTitle(title: String?): MyExceptionDialog {
        this.title.text = title
        return this
    }

    fun setYesListener(
        text: Int,
        listener: DialogInterface.OnClickListener?
    ): MyExceptionDialog {
        yes.setText(text)
        positiveButtonClickListener = listener
        return this
    }

    fun setYesListener(
        text: String?,
        listener: DialogInterface.OnClickListener?
    ): MyExceptionDialog {
        yes.text = text
        positiveButtonClickListener = listener
        return this
    }

    fun setNoListener(
        text: Int,
        listener: DialogInterface.OnClickListener?
    ): MyExceptionDialog {
        no.setText(text)
        negativeButtonClickListener = listener
        return this
    }

    fun setNoListener(
        text: String?,
        listener: DialogInterface.OnClickListener?
    ): MyExceptionDialog {
        no.text = text
        negativeButtonClickListener = listener
        return this
    }

    private fun alphaShow() {
        val an = ObjectAnimator.ofFloat(layout, "alpha", 0f, 1f)
        an.duration = 1000
        an.start()
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        dialog = Dialog(context, R.style.BaseExceptionDialog)
        layout = inflater.inflate(R.layout.base_exception_dialog, null)
        dialog.addContentView(
            layout,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        title = layout.findViewById<View>(R.id.title) as TextView
        message = layout.findViewById<View>(R.id.message) as TextView
        yes = layout.findViewById<View>(R.id.yes) as Button
        no = layout.findViewById<View>(R.id.no) as Button
        yes.setOnClickListener {
            if (positiveButtonClickListener != null) {
                positiveButtonClickListener!!.onClick(
                    dialog,
                    DialogInterface.BUTTON_POSITIVE
                )
            }
            dialog.dismiss()
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        no.setOnClickListener {
            if (negativeButtonClickListener != null) {
                negativeButtonClickListener!!.onClick(
                    dialog,
                    DialogInterface.BUTTON_NEGATIVE
                )
            }
            dialog.dismiss()
        }
        dialog.setContentView(layout)
    }
}