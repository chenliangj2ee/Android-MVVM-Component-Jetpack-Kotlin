package com.chenliang.baselibrary.exception

import android.content.Context
import android.content.Intent
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class MyException private constructor() : Thread.UncaughtExceptionHandler {
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null
    private var mContext: Context? = null
    var mainActClass: Class<*>? = null
        private set
    var packages = ArrayList<String>()
    private val exceptions = HashMap<String, String?>()
    private val format = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
    private fun init(context: Context) {
        mContext = context
        packages.add(context.packageName)
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler() // 获取系统默认的UncaughtException处理器
        Thread.setDefaultUncaughtExceptionHandler(this) // 设置该CrashHandler为程序的默认处理器
    }

    fun addPackage(packageName: String): MyException {
        packages.add(packageName)
        return instance
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler!!.uncaughtException(thread, ex)
        } else {
        }
    }

    fun handleException(ex: Throwable?): Boolean {
        return ex?.let { crash(it) } ?: false
    }

    private fun crash(ex: Throwable?): Boolean {
        var boo = false
        var message = ""
        for (i in ex!!.stackTrace.indices) {
            for (ps in packages) {
                if (ex.stackTrace[i].className.contains(ps)) {
                    val ss1 = "错误原因: $ex\n"
                    val ss2 = """
                        相关类名: ${ex.stackTrace[i].className}
                        
                        """.trimIndent()
                    val ss3 = """
                        方法名称: ${ex.stackTrace[i].methodName}
                        
                        """.trimIndent()
                    val ss4 = """
                        错误行数: ${ex.stackTrace[i].lineNumber}
                        
                        """.trimIndent()
                    val ss5 = """
                        异常时间: ${format.format(Date())}
                        
                        """.trimIndent()
                    message = ss1 + ss2 + ss3 + ss4 + ss5
                    boo = true
                    break
                }
            }
        }
        if ("" == message) {
            for (ps in packages) {
                if (ex.fillInStackTrace().cause!!.stackTrace[0].className.contains(ps)) {
                    val ss1 = """
                        错误原因: ${ex.cause}
                        
                        """.trimIndent()
                    val ss2 = """
                        相关类名: ${ex.fillInStackTrace().cause!!.stackTrace[0].className}
                        
                        """.trimIndent()
                    val ss3 = """
                        方法名称: ${ex.fillInStackTrace().cause!!.stackTrace[0].methodName}
                        
                        """.trimIndent()
                    val ss4 = """
                        错误行数: ${ex.fillInStackTrace().cause!!.stackTrace[0].lineNumber}
                        
                        """.trimIndent()
                    val ss5 = """
                        异常时间: ${format.format(Date())}
                        
                        """.trimIndent()
                    message = ss1 + ss2 + ss3 + ss4 + ss5
                    boo = true
                }
            }
        }
        if (exceptions[message] == null) {
            if ("" == message) {
                crash(ex.cause)
            } else {
                val `in` = Intent(mContext, MyExceptionActivity::class.java)
                `in`.putExtra("message", message)
                `in`.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                mContext!!.startActivity(`in`)
                exceptions[message] = message
                Log.e("AsException", message)
                Log.e(
                    "AsException",
                    "-------------------------------------------------------------------------"
                )
                System.exit(1)
            }
        }
        return boo
    }

    companion object {
        private const val TAG = "AsException"
        val instance = MyException()
        fun open(con: Context, c: Class<*>?): MyException {
            val crashHandler = instance
            crashHandler.mainActClass = c
            crashHandler.init(con)
            return crashHandler
        }
    }
}