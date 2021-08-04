package com.chenliang.baselibrary.exception

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.widget.Toast
import com.chenliang.baselibrary.utils.log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class MyExceptionActivity : Activity() {
    var message: String? = null
    var dialog: MyExceptionDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        message = intent.getStringExtra("message")
        dialog = MyExceptionDialog(this@MyExceptionActivity)
        dialog!!.setTitle("尼玛，又出错了").setMessage(message)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setYesListener("保存图库") { d, which ->
            Handler().postDelayed(
                {
                    saveMyBitmap(
                        dialog!!.messageBitmap,
                        System.currentTimeMillis().toString() + ""
                    )
                }, 500
            )
        }
        dialog!!.setNoListener("重启") { d, which -> restartApp() }
        dialog!!.show()
        super.onResume()
    }

    fun saveMyBitmap(mBitmap: Bitmap, bitName: String): String {
        this.log("bitmap:$mBitmap")
        var path = sdcardPath + "/AstException"
        var f = File(path)
        if (!f.exists() && !f.isDirectory) {
            f.mkdir()
        }
        path = sdcardPath + "/" + bitName + ".jpg"
        f = File(path)
        if (f.exists()) return path
        var fOut: FileOutputStream? = null
        try {
            fOut = FileOutputStream(f)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            this.log("bitmap:" + e.message)
        }
        try {
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut!!.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        try {
            fOut!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        dialog!!.dismiss()
        this@MyExceptionActivity.sendBroadcast(
            Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(
                    "file://$path"
                )
            )
        )
        Toast.makeText(this@MyExceptionActivity, "正在保存，请稍后", Toast.LENGTH_SHORT).show()
        Toast.makeText(this@MyExceptionActivity, "保存成功", Toast.LENGTH_SHORT).show()
        finish()
        restartApp()
        return path
    }

    fun restartApp() {
        val intent = Intent(this, MyException.instance.mainActClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        this.startActivity(intent)
    }

    companion object {
        val sdcardPath: String
            get() = if (Environment.MEDIA_MOUNTED == Environment
                    .getExternalStorageState()
            ) {
                Environment.getExternalStorageDirectory().toString()
            } else {
                ""
            }
    }
}