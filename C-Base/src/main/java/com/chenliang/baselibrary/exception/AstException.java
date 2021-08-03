package com.chenliang.baselibrary.exception;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AstException implements UncaughtExceptionHandler {
    private static final String TAG = "AsException";
    private UncaughtExceptionHandler mDefaultHandler;
    private static AstException INSTANCE = new AstException();
    private Context mContext;
    private  Class act;
    ArrayList<String> packages=new ArrayList<String>();
    private HashMap<String,String> exceptions=new HashMap<String,String>();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private AstException() {
    }
    public static AstException getInstance() {
        return INSTANCE;
    }
    private void init(Context context) {
        mContext = context;
        packages.add(context.getPackageName());
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();// 获取系统默认的UncaughtException处理器
        Thread.setDefaultUncaughtExceptionHandler(this);// 设置该CrashHandler为程序的默认处理器
    }
    public static AstException open(Context con,Class c) {
        AstException crashHandler = AstException.getInstance();
        crashHandler.act=c;
        crashHandler.init(con);
        return crashHandler;
    }
    public Class getMainActClass(){
        return act;
    }
    public AstException addPackage(String packageName){
        packages.add(packageName);
        return INSTANCE;
    }
    public void uncaughtException(Thread thread, final Throwable ex) {

        if (!handleException(ex) && mDefaultHandler != null) {
        mDefaultHandler.uncaughtException(thread, ex);
        } else {

        }
    }
    public boolean handleException(Throwable ex) {
        if (ex == null)
        return false;
        return crash(ex);
    }
    private boolean crash(Throwable ex) {
        boolean boo = false;
        String message = "";
        for (int i = 0; i < ex.getStackTrace().length; i++) {
            for (String ps : packages) {
                if (ex.getStackTrace()[i].getClassName().contains(ps)) {
                String ss1 = "错误原因: " + ex.toString() + "\n";
                String ss2 = "相关类名: " + ex.getStackTrace()[i].getClassName() + "\n";
                String ss3 = "方法名称: " + ex.getStackTrace()[i].getMethodName() + "\n";
                String ss4 = "错误行数: " + ex.getStackTrace()[i].getLineNumber() + "\n";
                String ss5 = "异常时间: " + format.format(new Date()) + "\n";
                message = ss1 + ss2 + ss3 + ss4 + ss5;
                boo = true;
                break;
                }
            }

        }
        if ("".equals(message)) {
            for (String ps : packages) {
                if (ex.fillInStackTrace().getCause().getStackTrace()[0].getClassName().contains(ps)) {
                String ss1 = "错误原因: " + ex.getCause() + "\n";
                String ss2 = "相关类名: " + ex.fillInStackTrace().getCause().getStackTrace()[0].getClassName() + "\n";
                String ss3 = "方法名称: " + ex.fillInStackTrace().getCause().getStackTrace()[0].getMethodName() + "\n";
                String ss4 = "错误行数: " + ex.fillInStackTrace().getCause().getStackTrace()[0].getLineNumber() + "\n";
                String ss5 = "异常时间: " + format.format(new Date()) + "\n";
                message = ss1 + ss2 + ss3 + ss4 + ss5;
                boo = true;
                }
            }
        }

        if (exceptions.get(message) == null) {
                if("".equals(message)){
                     crash(ex.getCause());
                }else{
                    Intent in = new Intent(mContext, AsExceptionActivity.class);
                    in.putExtra("message", message);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(in);
                    exceptions.put(message, message);
                    Log.e("AsException", message);
                    Log.e("AsException", "-------------------------------------------------------------------------");

                    System.exit(1);
                }

        }
        return boo;
    }
}
